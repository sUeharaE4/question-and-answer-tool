FROM python:3.11-buster
ENV PYTHONUNBUFFERED=1

WORKDIR /work

ENV POETRY_HOME=/opt/poetry
RUN curl -sSL https://install.python-poetry.org | python -
RUN cd /usr/local/bin && ln -s /opt/poetry/bin/poetry

COPY pyproject.toml* poetry.lock* ./
RUN poetry config virtualenvs.in-project false
RUN poetry install --no-dev

ENTRYPOINT ["poetry", "run", "task", "start"]