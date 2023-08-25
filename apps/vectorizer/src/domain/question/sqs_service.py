import json
from typing import Generator

import boto3
import requests
from pydantic.dataclasses import dataclass

from ...config.application_properties import get_settings
from ..question.model.question import Question


@dataclass
class QAClient:
    """Client to fetch questions from qa-tool."""

    def __post_init__(self):
        """Set base url for qa-tool."""
        settings = get_settings()
        self.url_base = settings.qa_tool_rest_url

    def fetch_questions(
        self,
        tool_id: int,
        question_ids: list[int],
        questions_per_once: int = 10,
    ) -> Generator[list[Question], None, None]:
        """Fetch questions from qa-tool.

        Parameters
        ----------
        tool_id
            tool id
        question_ids
            list of question id
        questions_per_once : int, optional
            how many questions fetch from qa-tool, by default 10

        Returns
        -------
        Generator[list[Question], None, None]
            1 page of questions fetched from external api
        """
        endpoint = f"{self.url_base}/api/v1.0/qa/tools/{tool_id}/search"
        page = 0
        while True:
            query = (
                f"?ids={question_ids}&page={page}&size={questions_per_once}"
            )
            res = requests.get(endpoint + query)
            questions = [Question(**c) for c in res.json()["content"]]
            yield questions
            if res.json()["last"]:
                break
            page += 1


@dataclass
class SQSService:
    """SQS service."""

    region_name: str
    endpoint_url: str
    queue_name: str

    def __post_init__(self):
        session = boto3.Session(region_name=self.region_name)
        client = session.client(
            service_name="sqs", endpoint_url=self.endpoint_url
        )
        self.sqs_resource = client.get_queue_by_name(QueueName=self.queue_name)
        self.qa_client = QAClient()

    def fetch_messages(
        self, message_cnt: int = 1, questions_per_once: int = 10
    ) -> Generator[list[Question], None, None]:
        """Fetch messages from SQS and return questions.

        Parameters
        ----------
        message_cnt
            how many messages fetch from SQS, by default 1
        questions_per_once : int, optional
            how many questions fetch from qa-tool, by default 10

        Returns
        -------
        Generator[list[Question], None, None]
            questions fetched from external api
        """
        for message in self.sqs_resource.receive_messages(
            MaxNumberOfMessages=message_cnt
        ):
            message_json = json.loads(message.body)
            q_generator = self.qa_client.fetch_questions(
                message_json["tool_id"],
                message_json["upsert_ids"],
                questions_per_once,
            )
            for questions in q_generator:
                yield questions
