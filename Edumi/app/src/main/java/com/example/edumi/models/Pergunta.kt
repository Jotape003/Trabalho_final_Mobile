package com.example.edumi.models

data class Pergunta(
    var pergunta: String,
    var resposta: String,
    var expandida: Boolean = false
)

var perguntas = listOf(
    Pergunta(
        pergunta = "Como acesso minhas tarefas?",
        resposta = "Você pode acessar suas tarefas no menu principal, tocando em 'Tarefas' ou 'Atividades'.",
        expandida = false
    ),
    Pergunta(
        pergunta = "Consigo ver o boletim pelo aplicativo?",
        resposta = "Sim, vá até a seção 'Boletim' no menu para visualizar suas notas.",
        expandida = false
    ),
    Pergunta(
        pergunta = "Como entro em contato com a escola?",
        resposta = "Acesse a aba 'Comunicação' ou 'Contato' para enviar mensagens ou ver avisos da escola.",
        expandida = false
    ),
    Pergunta(
        pergunta = "É possível justificar faltas pelo app?",
        resposta = "Sim, na aba de frequência, você pode justificar faltas com um comentário ou anexo.",
        expandida = false
    ),
    Pergunta(
        pergunta = "Como alterar minha senha?",
        resposta = "Vá até as configurações do perfil e selecione 'Alterar senha'.",
        expandida = false
    ),
    Pergunta(
        pergunta = "O que faço se esquecer minha senha?",
        resposta = "Na tela de login, toque em 'Esqueci minha senha' e siga as instruções para redefinir.",
        expandida = false
    ),
    Pergunta(
        pergunta = "Posso acessar o app em mais de um dispositivo?",
        resposta = "Sim, você pode acessar com o mesmo login em vários dispositivos, mas evite acessos simultâneos.",
        expandida = false
    ),
    Pergunta(
        pergunta = "Como atualizo meus dados pessoais?",
        resposta = "Acesse seu perfil e toque em 'Editar' para atualizar nome, telefone, e-mail, entre outros.",
        expandida = false
    ),
    Pergunta(
        pergunta = "O que fazer se o app travar ou não abrir?",
        resposta = "Tente fechar o app e abrir novamente. Se o problema continuar, atualize ou reinstale o aplicativo.",
        expandida = false
    ),
    Pergunta(
        pergunta = "Como recebo notificações da escola?",
        resposta = "Certifique-se de permitir notificações nas configurações do app e do seu celular.",
        expandida = false
    )

)