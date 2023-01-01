# classificados-veiculos

Atividade avaliativa da disciplina de Sistemas Distribuídos.

# Como executar

Os seguintes programas precisam estar abertos para a aplicação funcionar:

* ActiveMQ
* H2 Database

Após isto, execute a classe ´ClassificadosVeiculosApplication.java´ para iniciar a aplicação Spring Boot.

A classe Produtor pode ser utilizada para a inserção de veiculos no banco, enviando os dados por meio da fila de mensagens do ActiveMQ.

A classe Consumidor recebe (por fila de mensagens) e insere (via REST) os veiculos no banco de dados. Também é possível visualizar a tabela de veiculos por meio desta classe pressionando ´Enter´ a qualquer momento.

A tabela também pode ser visualizada por meio do arquivo **veiculos.html**, localizado no diretório **html**.
