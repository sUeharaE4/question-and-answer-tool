from datetime import datetime

from pydantic import BaseModel


class Question(BaseModel):
    id: int
    tool_id: int
    question: str
    answer: str
    created_at: datetime
    updated_at: datetime

    @classmethod
    def __index_name__(cls) -> str:
        """Create an index name for the question model."""
        return cls.__name__.lower()
