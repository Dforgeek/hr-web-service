from typing import List
import time
import pandas as pd
from fastapi import FastAPI
from sqlalchemy import create_engine
from data_models import *
from sbert import SBERT
import ast
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity

app = FastAPI()

start_time = time.time()
sbert_model = SBERT()
end_time = time.time()
print(f"Time taken to launch SBERT: {end_time - start_time} seconds")

engine = create_engine('postgresql+psycopg2://postgres:postgres@51.250.103.114:5432/hrBackDb')

query = "SELECT * FROM resume"  # исрпавить на корректный селект
resume_data = pd.read_sql(query, engine)
print('Columns of table:', resume_data.columns)
start_time = time.time()
resume_embeddings = np.stack(resume_data['Embeddings'].apply(lambda x: np.array(ast.literal_eval(x))))
end_time = time.time()
print(f"Time taken to get resume embeddings: {end_time - start_time} seconds")


@app.get("/")
async def read_root():
    return {"Hello": "World"}


@app.get("/get_by_id/{id}")
async def get_row(id: int):
    return resume_data.iloc[id][['Position', 'Skills']]


@app.post("/get_range/")
async def give_similarities(vacancy: Vacancy) -> List[SimilarityUnit]:
    position = 'Позиция: ' + vacancy.position
    skills = 'Обязательные навыки: ' + vacancy.key_skills
    would_be_a_plus = 'Важные навыки, но не обязательные: ' + vacancy.would_be_a_plus
    about_you = 'Обо мне: ' + vacancy.soft_skills  # описание софтов кандидата
    vacancy_description = 'Предстоит делать: ' + vacancy.description

    final_description = position + skills + vacancy_description + would_be_a_plus + about_you

    start_time = time.time()
    vacancy_embedding = sbert_model.encode(final_description).numpy()  # TODO: нормальное чтение полей
    end_time = time.time()
    print(f"Time taken to make embedding: {end_time - start_time} seconds")

    similarities = cosine_similarity(vacancy_embedding, resume_embeddings).flatten()
    similarity_units = [SimilarityUnit(id=i, similarity=sim) for i, sim in enumerate(similarities)]

    similarity_units.sort(key=lambda x: x.similarity, reverse=True)

    return similarity_units


@app.post("/update_resumes/")
async def update_resumes():
    global resume_data
    global query
    global engine
    resume_data = pd.read_sql(query, engine)
