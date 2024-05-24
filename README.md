# <a name="topo"></a>SQL Chat Bot

![Descrição do GIF](src/main/resources/static/img/SQLBot.png)

O projeto visa criar uma aplicação que permite acessar informações de um banco de dados MySQL usando exclusivamente a linguagem natural. A aplicação deve ser capaz de interpretar a linguagem natural e traduzi-la para uma consulta SQL, executando-a e retornando o resultado ao usuário.

### Requisitos Funcionais
<ul>
<li>RF1 - Permitir ao usuário selecionar um banco de dados</li>
<li>RF2 - Criação de uma tela para receber uma consulta escrita em linguagem natural</li>
<li>RF3 - Conversão da linguagem natural em código SQL</li>
<li>RF4 - Execução do comando SQL gerado no banco de dados</li>
<li>RF5 - Exibição dos resultados</li>
</ul>

### Requisitos Não Funcionais
<ul>
<li>RNF1 - Manual do Usuário (GitHub)</li>
<li>RNF2 - Guia de instalação (GitHub)</li>
<li>RNF3 - Usabilidade</li>
</ul>


### Características
<ul>
<li>Interface Gráfica projetada para proporcionar uma experiência de usuário intuitiva, facilitando a utilização das funcionalidades disponíveis.</li>
<li>Compatibilidade com LLM SQLCoder: o software demonstra um excelente desempenho ao trabalhar em conjunto com uma variedade de modelos de linguagem SQLCoder.</li>
<li>Os usuários conseguem selecionar e integrar facilmente qualquer banco de dados disponível em suas máquinas.</li>
<li>O software possui a capacidade de alterar para qualquer modelo de linguagem instalado no computador.</li>
<li>Testes realizados em Português (BR) e Inglês (EUA)</li>
</ul>

## Demonstração
Demonstração do funcionamento geral do SQL Bot:

<img src="src/main/resources/static/gifs/sprint4.gif" alt="GIF sprint 4" width="400"/>

### Chat Funcional (Sprint 2)
Na Sprint 2, o chat passou a ser funcional para pesquisas, permitindo que o sistema receba perguntas em linguagem natural e retorne respostas em formato de tabela, contendo todos os dados relevantes.

<img src="src/main/resources/static/img/sprint2.png" alt="img sprint 2" width="400"/>

### Alteração do Banco de Dados (Sprint 3)

Na Sprint 3, foi incluída a função de modificação do banco de dados, permitindo a realização de consultas em qualquer banco de dados que o usuário possua.

<img src="src/main/resources/static/img/sprint3.png" alt="img sprint 3" width="400"/>

### Alteração do Modelo de Linguagem (Sprint 4)

Na Sprint 4, foi adicionada a função de alterar qualquer modelo de linguagem que o usuário possua, proporcionando uma personalização avançada.

<img src="src/main/resources/static/img/sprint4.png" alt="img sprint 4" width="400"/>


<details>
<summary><h1>Backlogs</h1></summary>

<details><summary><h2>Sprint 2</h2></summary>

| Rank | Prioridade | User Story | Estimativa | Sprint | Requisito do Parceiro | Critério de aceitação |
|------|------------|------------|------------|--------|-----------------------|------------------------|
| 1   | ALTA       | Eu, como usuário, quero usar uma interface do usuário finalizada para ter uma experiência consistente e intuitiva ao navegar e interagir com o sistema. | 5 | 2 | RF2 | A interface do usuário deve estar finalizada e proporcionar uma experiência consistente e intuitiva. |
| 2   | ALTA       | Eu, como usuário, quero inserir perguntas na interface do sistema em linguagem natural para facilitar a interação | 3 | 2 | RF2 | A interface deve permitir a inserção de perguntas em linguagem natural de forma clara e precisa. |
| 3   | ALTA       | Eu, como desenvolvedor, quero converter perguntas em linguagem natural para consultas SQL para executar no banco de dados | 13 | 2 | RF3 | O sistema deve converter perguntas em linguagem natural em consultas SQL corretas e eficientes. |
| 4  | ALTA       | Eu, como desenvolvedor, quero executar comandos SQL no banco de dados para obter resultados relevantes para o usuário | 8 | 2 | RF4 | O sistema deve ser capaz de executar comandos SQL no banco de dados de forma segura e eficiente. |
| 5  | ALTA       | Eu, como usuário, quero ver os resultados das consultas exibidos na tela para tomar decisões com base neles | 7 | 2 | RF5 | Os resultados das consultas devem ser exibidos de forma clara e organizada na tela. |
</details>
<details><summary><h2>Sprint 3</h2></summary>

| Rank | Prioridade | User Story | Estimativa | Sprint | Requisito do Parceiro | Critério de aceitação |
|------|------------|------------|------------|--------|-----------------------|------------------------|
| 6  | MÉDIA      | Eu, como usuário, quero ter a capacidade de alterar o banco de dados que está sendo consultado para explorar diferentes conjuntos de dados | 10 | 3 | RF1 | O sistema deve permitir que o usuário alterne entre diferentes bancos de dados de forma fácil e intuitiva. |
| 7 | BAIXA      | Eu, como usuário, quero que a interface do sistema seja melhorada para tornar a interação mais intuitiva e agradável | 5 | 3 | RF2 | As melhorias na interface do sistema devem resultar em uma interação mais intuitiva e agradável para o usuário. |

</details>

<details><summary><h2>Sprint 4</h2></summary>

| Rank | Prioridade | User Story | Estimativa | Sprint | Requisito do Parceiro | Critério de aceitação |
|------|------------|------------|------------|--------|-----------------------|------------------------|
| 8 | MÉDIA      | Eu, como usuário, quero poder selecionar o modelo de linguagem que está sendo usado para fazer as consultas para personalizar a experiência | 10 | 4 | RF3 | O sistema deve permitir que o usuário selecione entre diferentes modelos de linguagem para realizar consultas de acordo com suas preferências. |
| 9  | BAIXA      | Eu, como usuário, quero poder ler o manual do aplicativo para entender suas funcionalidades e como usá-lo | 5 | 4 | RNF1 | O manual do aplicativo deve estar disponível de forma clara e acessível para o usuário. |
| 10  | BAIXA      | Eu, como usuário, quero poder ler o guia de instalação para o funcionamento do aplicativo para garantir uma instalação correta | 4 | 4 | RNF2 | O guia de instalação deve fornecer instruções claras e precisas para uma instalação correta do aplicativo. |

</details>
</details>

## MVP

![MVP](src/main/resources/static/img/mvp.png)

## Tecnologias

<p>
   <img align="left" title="java" height="30px" src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/Java-Dark.svg"/> &nbsp;Java - Linguagem de programação
 </p>
 
<p>
  <img align="left" title="figma" height="30px" src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/Figma-Dark.svg"/> &nbsp;Figma - Prototipagem
 </p>

<p>
   <img align="left" title="github" height="30px" src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/Github-Dark.svg"> &nbsp;GitHub - Versionamento
 </p>

<p>
   <img align="left" title="vscode" height="30px" src="https://hermes.digitalinnovation.one/articles/cover/14f7ca38-af7d-4b21-a0d9-c581c2611e9b.png"/>
   &nbsp; IntelliJ - IDEA
 </p>

<p>
   <img align="left" title="mysql" height="30px" src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/MySQL-Dark.svg"/>
   &nbsp;MySQL - Banco de dados
 </p>
 
<p>
   <img align="left" title="langchain4j" height="30px" src="https://avatars.githubusercontent.com/u/132277850?v=4"/>
   &nbsp;LangChain4j - Biblioteca Java
 </p>

<p>
   <img align="left" title="ollama" height="30px" src="https://ollama.com/public/assets/c889cc0d-cb83-4c46-a98e-0d0e273151b9/42f6b28d-9117-48cd-ac0d-44baaf5c178e.png"/>
   &nbsp;Ollama - Executor de modelos de linguagem
 </p>

 <p>
   <img align="left" title="ollama" height="30px" src="https://pbs.twimg.com/profile_images/1755060270173429760/4WVc54_p_400x400.jpg"/>
   &nbsp; LM Studio - Executor de modelos de linguagem
 </p>


## Equipe

Somos alunos do 2° semestre do curso de Análise e Desenvolvimento de Sistemas da instituição de ensino Fatec Prof° Jessen Vidal de São José dos Campos.

|    Função     |             Nome             |                                                                           GitHub                                                                            |                                                                                                                              LinkedIn                                                                                                                               |
|:-------------:|:----------------------------:|:-----------------------------------------------------------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| Product Owner |    Daniel Sendreti Broder    |    <a href="https://github.com/d-broder"><img height="30px" src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/Github-Dark.svg"></a>    |                                                  <a href="https://www.linkedin.com/in/danielbroder/"><img height="30px" src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/LinkedIn.svg"></a>                                                   |
| Scrum Master  |  Guilherme Cleyton Pereira   |     <a href="https://github.com/gui863"><img height="30px" src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/Github-Dark.svg"></a>     |                                       <a href="https://www.linkedin.com/in/gu%C3%ADlherm-p%C3%AAreira-7993aa7a/"><img height="30px" src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/LinkedIn.svg"></a>                                       |
|   Dev Team    |    Gabriel Carvalho Silva    | <a href="https://github.com/Gabriecarvalho"><img height="30px" src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/Github-Dark.svg"></a> |                                           <a href="https://www.linkedin.com/in/gabriel-carvalho-30598b292/"><img height="30px" src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/LinkedIn.svg"></a>                                            |
|   Dev Team    | Gabriel Vasconcelos Ferreira |  <a href="https://github.com/gabrielvascf"><img height="30px" src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/Github-Dark.svg"></a>  | <a href="https://www.linkedin.com/in/gabriel-vasconcelos-255979262?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app"><img height="30px" src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/LinkedIn.svg"></a> |
|   Dev Team    |     Paloma Lima da Silva     |  <a href="https://github.com/palomalima22"><img height="30px" src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/Github-Dark.svg"></a>  |                                              <a href="https://www.linkedin.com/in/paloma-lima-b2b00b201/"><img height="30px" src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/LinkedIn.svg"></a>                                              |
|   Dev Team    |    Victor Herculano Godoy    |  <a href="https://github.com/victorrgodoy"><img height="30px" src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/Github-Dark.svg"></a>  |                                                  <a href="https://www.linkedin.com/in/victorgodoy-/"><img height="30px" src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/LinkedIn.svg"></a>                                                   |

[Voltar ao topo](#topo)

 
