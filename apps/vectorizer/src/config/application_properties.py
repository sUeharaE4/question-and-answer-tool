"""Provide properties for this application."""
from pathlib import Path

from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    """Provide settings for the app."""

    app_name: str = "vectoring text"
    src_root: Path = Path(__file__).parents[1]
    aws_region: str = "ap-northeast-1"
    aws_sqs_endpoint: str = "http://localstack:4546/"
    aws_sqs_name: str = "qa.fifo"
    qa_tool_rest_url: str = "http://qa-tool:7002"


def get_settings():
    """Provide settings for the app."""
    return Settings()
