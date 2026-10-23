# App Relógio

App Android de relógio que entra em **tela cheia** quando o celular é girado para a horizontal.

## Como abrir

1. Abra a pasta `app-relogio` no Android Studio (versão Ladybug ou mais nova).
2. Espere o Gradle sincronizar.
3. Rode no celular ou emulador (Android 8.0+). Gire a tela para ver o modo tela cheia.

## O que já faz

- Relógio com horas, segundos e data, atualizado a cada segundo (segue o formato 12/24h do aparelho).
- Números em fonte de 7 segmentos, estilo relógio digital de mesa ([DSEG7](https://github.com/keshikan/DSEG), licença OFL em `licenses/`).
- Em paisagem: fundo preto, dígitos grandes, barras do sistema escondidas e tela sempre ligada.
- Em paisagem: ícone de carregamento com a porcentagem da bateria enquanto o carregador estiver conectado.
- Em retrato: barra inferior para trocar entre **Relógio** e **Pomodoro** (Pomodoro ainda é só um espaço reservado).

## Estrutura

```
app/src/main/java/com/apprelogio/
├── MainActivity.kt            ponto de entrada
└── ui/
    ├── AppMode.kt             lista de modos (Relógio, Pomodoro…)
    ├── AppRelogio.kt          troca de modo + tela cheia em paisagem
    ├── clock/                 tela do relógio
    ├── pomodoro/              tela do Pomodoro (próxima etapa)
    ├── system/ImmersiveMode.kt  esconde barras e mantém a tela ligada
    └── theme/                 cores (Material 3, cores dinâmicas no Android 12+)
```

## Próximos passos

- Modo Pomodoro (foco/pausa, ciclos, notificação ao terminar).
- Integração com app de música (futuro).
