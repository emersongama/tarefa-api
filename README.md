# TAREFA-API

[![Java CI with Maven](https://github.com/emersongama/tarefa-api/actions/workflows/maven.yml/badge.svg)](https://github.com/emersongama/tarefa-api/actions/workflows/maven.yml)

API feita com Spring Boot e Java 11.

- Github: https://github.com/emersongama/tarefa-api
## Requisitos

- JDK 11
- Maven 3+

## Executar API com o Docker

- Certifique-se de que sua máquina possui o docker instalado, caso não possua, ele pode ser baixado no seguinte endereço: https://www.docker.com/get-started;
- Abra o terminal no diretório do projeto, onde está o arquivo docker-compose.yml;
- Execute o compose para montar a stack no docker:

```bash
docker-compose up -d
```
- A API ficará disponível em: http://localhost:8081 e o banco em http://localhost:5432.