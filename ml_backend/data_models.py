from pydantic import BaseModel

class Vacancy(BaseModel):
    position: str
    description: str
    soft_skills: str
    would_be_a_plus: str
    key_skills: str


class SimilarityUnit(BaseModel):
    id: int
    similarity: float
