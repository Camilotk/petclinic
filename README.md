<h1 align="center">
  <img src="https://uploaddeimagens.com.br/images/004/433/031/original/petshop-removebg-preview.png" alt="ocaml">
</h1>

API em Spring Boot de um Sistema de Controle de atendimentos de petshops.

## Como rodar o projeto

### Pré-requisitos
- Java 17 ou superior
- Maven
- PostgreSQL

### Configuração
Configurar as chaves de sistema:
```
POSTGRES_USER=
POSTGRES_PASSWORD=
POSTGRES_DB=
POSTGRES_PORT=
```

### Executar o Projeto
Dentro do terminal bash rode
```
./mvnw spring-boot:run
```

### Executar os Testes
Dentro do terminal bash rode
```
./mvnw test
```

## Requisitos
<details>

![](https://uploaddeimagens.com.br/images/004/433/038/original/objetivo_back_petshop.png)

</details>

## Conceitos utilizados
- [MVC Pattern](https://pt.wikipedia.org/wiki/MVC)
- [The Twelve Factor App](https://12factor.net/pt_br/)
- [JWT Stateless Authentication](https://jwt.io/)
- [Richardson API Maturity Model Level 2](https://en.m.wikipedia.org/wiki/Richardson_Maturity_Model)
