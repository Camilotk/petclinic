# Pet Clinic

Sistema de controle de atendimentos de uma petshop.

<!-- Connect Database (java -cp  /home/cazevedo/.m2/repository/org/hsqldb/hsqldb/2.7.1/hsqldb-2.7.1.jar org.hsqldb.util.DatabaseManagerSwing) -->


## Acessar o Banco de Dados

O projeto está utilizando o H2.

- [localhost:8080/h2-console/](localhost:8080/h2-console/)

com credenciais:

- **user**: sa
- **password**: asdf

## Rotas

| Link | Verbo HTTP | O que faz? |
| :--  | :-:        | :-         |
| http://localhost:8080/races        | GET    | Recebe todas as raças de Pet          |
| http://localhost:8080/races/\{id\} | GET    | Recebe uma Raça de Pet com \{id\}     |
| http://localhost:8080/races        | POST   | Cria uma Raça de Pet (requisição)     |
| http://localhost:8080/races/\{id\} | PUT    | Atualiza uma Raça de Pet (requisição) |
| http://localhost:8080/races/\{id\} | DELETE | Deleta uma Raça de Pet                |


## Requisições POST

### Criar um novo registro de Raça
```
URL: http://localhost:8080/races
Method: POST
Headers: 
    Content-Type: application/json
Body:
{
    "description": "Labrador Retriever"
}
```

### Atualizar o registro de uma Raça
```
URL: http://localhost:8080/races/1
Method: PUT
Headers: 
    Content-Type: application/json
Body:
{
    "description": "Collie"
}
```

