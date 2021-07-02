<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<title><fmt:message key="title.about"/></title>
		
		<c:import url="../head.jsp"/>
	</head>
	
	<body>
		<c:import url="../header.jsp"/>
		
		<article class="container">
			<h1><fmt:message key="title.about"/></h1>
			
			<section>
				<p><fmt:message key="section.definition"/></p>
			</section>
			
			<section>
				<h2>Problemas gerados pelas redes sociais tradicionais? <strong>Tizito</strong>, a solução!</h2>
				
				<p>
					A aplicação Tizito, surge na tentativa de curar um dos maiores problemas da nossa sociedade, o vício das redes sociais, as obsessões relacionadas com as mesmas e a toxicidade que estas quando mal utilizadas, podem causar. 
					Surge também da necessidade de existir cada vez mais uma reaproximação das pessoas, pois não só nesta fase em que nos encontramos, de pandemia, como até antes de esta surgir, tem-se vindo a notar um afastamento das pessoas, principalmente entre amigos ou familiares. 
					A maior parte das vezes, esse contacto humano dos “nossos”, tem vindo a ser substituído pelas redes sociais comuns. Cuja finalidade, é provocar um vício, nada saúdavel para a nossa saúde mental. 
					Como tal, o nosso projeto tem como objetivo promover encontros pessoais entre amigos, ou seja, pessoas que fazem parte do nosso círculo de convívio e nos são realmente próximas, como familiares, amigos, colegas de trabalho ou escola/faculdade.
					Este projeto, é portanto uma rede social, mas ao contrário das redes sociais comuns, a finalidade é cimentar as relações já existentes e não acrescentar relações de amizade ou seguidores, pretende estimular relações no mundo real através de um “convite” virtual. 
					Vai funcionar no fundo, como a nossa agenda social, podemos até fazer a analogia de um post-it deixado num qualquer local estratégico com o objetivo de relembrar algo, esta aplicação fará o mesmo informando quando, onde e o que fazer num feriado ou final de semana, após uma aula ou dia de trabalho, por exemplo. 
				</p>

				<p>
					Achamos que esta ideia irá combater no fundo problemas sociológicos, psicológicos e relacionados com a privacidade, no sentido em que aproximará as pessoas no mundo real, isto fisicamente, e não ser necessário existir longas conversas por chats ou similares para simplesmente definir detalhes de um encontro, seja ele uma comemoração ou uma simples caminhada. 
					O tempo poupado nesses detalhes será destinado ao que verdadeiramente importa. O tempo para estarmos com quem mais queremos. O objetivo mais complexo é também retirar um pouco o vício dos objetivos das outras redes sociais que promovem a exposição e a falta de privacidade, através dos conteúdos postados como fotos e vídeos, ou até mesmo os likes e comentários que muitas vezes podem ser com carácter ofensivo.
					E neste sentido é que promovemos a ideia de que os dados de um encontro (quando, onde e o que fazer) pertencem apenas à pessoa que convida (aquele que marca o encontro) e aos seus convidados, desde que estes tenham aceite o convite. Portanto, não existirá visualização desses dados por outros amigos ou utilizadores, nem a partilha de fotos, vídeos e comentários.
				</p>

				<p>
					Acima de tudo, o que é pretendido é que seja cumprido o objetivo de uma forma muito simples, intuitiva e que seja de uso fácil.
				</p>
			</section>
			
			<section>
				<h2>Tizito: Funcionalidades</h2>
				
				<p>
					O Tizito encontra-se dividido em entre várias funcionalidades em que a principal é mesmo a possibilidade de marcar encontros. 
					Começando pelo processo de autenticação, onde o utilizador procede à Criação de registo de conta e Login. Sem essas duas funcionalidades não é possível interagir com as demais funcionalidades da aplicação. Primeiramente o utilizador tem que criar uma conta. Para o seu registo, o utilizador apenas tem que fornecer o seu nickname e uma password, que serão futuramente utilizados como credenciais de Login.
					Passando aos vínculos de amizade, esta encontra-se dividida pela adição de contactos e na consulta da lista de amigos. É possível adicionar um amigo, simplesmente ao pesquisar pelo seu nickname. Quando o contacto/perfil pretendido é encontrado, é feito um pedido, que pode ser aceite ou não. 
					Supondo que o pedido anteriormente referido é aceite, o contacto adicionado passa a pertencer à sua lista de amigos, e vice versa, a ligação de amizade é bidirecional. Para a consulta desses amigos existirá uma lista, essa lista de amigos é privada para cada utilizador, eu como utilizador apenas tenho acesso à minha lista de amigos, eu não posso consultar a de outros utilizadores e vice-versa. 
					A funcionalidade da marcação de encontros, como o nome indica, é onde é possível selecionar todos os detalhes necessários para a marcação desse mesmo encontro e consulta do encontros marcados. 
					Surgirá um formulário onde o primeiro passo é a escolha de quem vai participar no encontro, para tal só é possível escolher amigos da lista de amigos. Após a escolha dos participantes do encontro, o utilizador escolhe a data e hora do encontro e por fim, o local. Para a escolha do local, é utilizado um mapa (Google maps) de forma a ser mais fácil a obtenção das coordenadas geográficas.
					Depois do evento estruturado, este é marcado e enviado para os convidados, estes podem ou não aceitar participar. No caso da recusa do evento, este desaparece automáticamente da lista de eventos. Lista essa de eventos em que o utlizador só tem os eventos para os quais aceitou comparecer, os eventos por aceitar estarão na lista dos pendentes.
				</p>
			</section>
			
			<section>
				<h2>Caracterização do Sistema</h2>
				
				<p>
					Consideramos que o nosso sistema da aplicação apresenta algumas características importantes como é o caso da <strong>Portabilidade</strong>, pois esta está disponível tanto para o sistema operativo Android como para o sistema IOS. 
				</p>
				
				<p>
					A nível de <strong>Usabilidade</strong>, o Tizito é uma aplicação user friendly, respeita tudo aquilo que é necessário para a satisfação do utilizador enquanto a utiliza, quer a nível do esquema de cores, à estrutura dos elementos como os botões e é também uma aplicação acessível, tudo para facilitar o processo de aprendizagem e facilidade para o utilizador. Possui também um nível elevado de <strong>Eficiência</strong>, pois executa tudo aquilo que a aplicação promete de forma rápida e eficaz, apresenta poucas falhas.
				</p>
				
				<p>
					É também um sistema que tem muita <strong>facilidade de manutenção</strong> devido à organização, numenclatura e notações utilizadas, facilmente quem não conhece a aplicação percebe a estrutura. A documentação efetuada, ajuda bastante, também.
				</p>
			</section>
			
			<section>
				<h2>Caracterização dos Utilizadores</h2>
				
				<p>
					Esta aplicação é dirigida a <strong>todas as pessoas</strong>, sem exepção, não existe um público alvo restrito. Para a utilização do Tizito apenas é preciso ter um SmartPhone e muita vontade de socializar pessoalmente com os que nos são mais próximos. O virtual apenas serve de ponte para aproximar do mundo real. Apesar de o Tizito ser uma rede social, o conceito desta permite que quem não “morre de amores” pelas tradicionais redes sociais a possa utilizar sem estar sujeito aos típicos problemas impostos por elas.
				</p>
			</section>
			
			<section>
				<h2>Solução Tecnológica</h2>
				
				<p>
					No desenvolvimento da aplicação foi utilizada a framework <strong>Flutter</strong>,  na linguagem <strong>Dart</strong>, de modo a que pudessemos desenvolver a aplicação tanto para Android como para IOS, aproveitando a composição dos Widgets já definidos pela própria framework, de modo a agilizar este processo. Neste projeto não foi criado nenhum Widget, utilizamos todos os blocos já prontos e desenvolvidos. 
				</p>
				<p>
					A nível da nossa API que comunica com a base de dados e aplicação Móvel /Web utilizámos a linguagem <strong>Java</strong> para o seu desenvolvimento e foram utilizadas as <strong>especificações CDI</strong> para tratar da injeção de dependências, <strong>JPA</strong> para a persistência dos dados e <strong>JAX-RS</strong> para a geração de Endpoints. A arquitetura utilizada, foi a arquitetura <strong>REST</strong>.
				</p>
				<p>
					A nossa WebApp foi desenvolvida simplesmente em <strong>HTML</strong>.				
				</p>
				<p>
					A nível de base de dados foi utilizada <strong>MariaDB</strong>, com o objetivo de manter alto desempenho e fidelidade, como no MySQL e exisitir uma compatibilidade com este a nível de bibliotecas, comandos, interfaces e APIs. A segurança e o Não existir necessidade de conversão de dados, foram outros dos pontos que nos fizeram optar pelo MariaDB.				
				</p>
				<p>
					Tanto a base de dados, como a API e aplicação web, estão alojadas em servidores Hetzner distintos.				
				</p>
				
				
				<p>
					A nível de desafios, o mais complicado foi a inexperiência do grupo de trabalho com a utilização da linguagem Dart no Flutter no desenvolvimento da aplicação móvel e a sua integração com a API. O facto de termos escolhido um tema muito amplo e complexo para apenas dois elementos num curto espaço temporal, também não ajudou.				
				</p>
			</section>
			
			<section>
				<h2>Arquitetura do Sistema</h2>
				
				<img id="arquitetura" alt="Arquitetura" src="<c:url value='/img/arquitetura.jpeg'/>" class="img-thumbnail">
			
			</section>
			
			<section>
				<h2>Testes de utilizadores</h2>
				
				<p>
					Ao longo de todo o processo de análise, desenho e implementação da aplicação, foi escolhida uma amostra de 10 utilizadores para a elaboração de testes de usabilidade. De ressalvar que a escolha destes utilizadores não teve nenhum critério em específico, devido ao Tizito ser uma aplicação aberta e disponível para todas as pessoas, como referido anteriormente na designação do público alvo. 
				</p>
				
				<p>
					Estes testes foram aplicados através um questionário de usabilidade, onde foi respondido um conjunto de perguntas, onde na maioria foram pretendidas respostas escaladas de forma a que fosse possível fazer uma análise, sempre com o intuito de melhorar as funcionalidades da aplicação ou até mesmo de obter novas ideias e informações.
				</p>
				
				<p>
					Com base nestes testes e com a opinião da nossa amostra de utilizadores, foi sempre possível obter um feedback, na sua maioria positivo, acompanhado por críticas construtivas que nos permitiram sempre melhorar o aspeto da aplicação, quer a nível de cores, de aspeto ou estrutura. Em muitos casos, tornaram o conceito e utilização da aplicação mais fácil de usar e aprender, assim como tornar o aspeto mais apelativo e mais acessível. Porém uma das maiores críticas apontadas é a aplicação não ter mais funcionalidades. Algo a resolver futuramente...
				</p>
			</section>
		</article>
		
		<c:import url="../footer.jsp"/>
	</body>
</html>