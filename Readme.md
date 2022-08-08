ToDo - Marcone Augusto

## Engenharia de Telecomunicações

#### Instituto Federal de Santa Catarina - IFSC

## Problemas propostos

### 1
 Um aluno está desenvolvendo seu TCC na área da Internet das Coisas (Internet of Things – IoT)
 e para tal precisará de 4 placas Raspberry PI com Raspbian Stretch instalado.
 Para não ter que conectar um monitor e teclado em cada uma das placas, ele optou por instalar um servidor ssh (sudo apt install openssh-server)
 e assim poderá acessar remotamente cada uma delas. Porém, como cada placa obtém endereço IP de forma dinâmica,
 via DHCP, o aluno está com dificuldade em saber o IP que cada placa pegou. O problema é agravado pelo fato do mobilidade desse kit.
 Isto é, o aluno costuma levar esse conjunto de placas para sua casa e também para conferências das quais participa.
 Desenvolva uma solução para facilitar a descoberta de IP e serviços que estão rodando em cada uma dessas placas.
 A solução a ser proposta não poderá fazer uso de mDNS ou DDNS .

### Solução proposta
Desenvolvimento de quatro aplicações clientes e uma servidora com o uso do pacote Java RMI.
projeto-prático-01-MarconeAugusto/STD/src/main/java/std29006/Projeto_01/
Para execução do servidor:  "Ip do servidor"
Para execução dos clientes: "Ip do servidor"

### 2

Um dos experimentos que o aluno deve desenvolver com seu conjunto de Raspberry PI é o de multiplicação de matrizes por blocos.
Nesse experimento, as matrizes são divididas em 4 blocos, sendo 4 o número total de processos, que no caso,
será equivalente ao número total de Raspberry PI ativas. Nesse experimento, o computador do aluno deve disparar tarefas para todas as Raspberry PI,
coletar os resultados das multiplicações de cada bloco, consolidar e apresentar os resultados. O experimento só pode ser iniciado se houverem 4 Raspberry ativas.
Sendo assim, o aplicativo no computador do aluno será responsável pela sincronização dos processos.

### Solução proposta
Desenvolvimento de quatro aplicações clientes e uma servidora com o uso do pacote Java RMI.
A implementação só provê a realização do cálculo para matrizes que tenham números pares de linhas e colunas.
projeto-prático-01-MarconeAugusto/STD/src/main/java/std29006/Projeto_02/
Para execução do servidor:  "Ip do servidor" "número de placas" "nº de linhas da 1ª matriz" "nº de colunas da 1ª matriz" "nº de linhas da 2ª matriz" "nº de colunas da 2ª matriz"
Para execução dos clientes: "Ip do servidor"