# SellerDepartmentManager 

# 📌 Sobre o Projeto

Este projeto é um sistema de gerenciamento de vendedores e departamentos que permite cadastrar, atualizar, buscar e excluir no banco de dados. A aplicação utiliza Java, JDBC e PostgreSQL para manipulação dos dados.

# 🚀 Tecnologias Utilizadas

Java 11+

JDBC

PostgreSQL

Maven (para gerenciamento de dependências)

# ⚙️ Configuração do Banco de Dados e Projeto

1. Antes de rodar a aplicação, certifique-se de ter um banco de dados PostgreSQL configurado. Execute comandos presentes no arquivo database.sql para a criação do mesmo
2. Configure o banco de dados no arquivo db.properties:
```properties
db.url=jdbc:postgresql://localhost:5432/seu_banco
db.user=seu_usuario
db.password=sua_senha
```

# 🛠 Funcionalidades
Para realizar os teste da funcionalidades presentes no programa basta acessar as classes DepartmentMain e SellerMain. Em cada uma delas estão presentes os respectivos métodos capazes de alterar ou buscar os dados prevenientes do banco.
