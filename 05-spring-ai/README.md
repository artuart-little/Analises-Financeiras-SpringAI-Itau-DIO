# 🧠 Spring AI Budgeting - Financial Analytics Toolkit

> **Desafio de Projeto:** Evolução de uma API de controle de orçamento com Inteligência Artificial (Spring AI), desenvolvido como entrega final do bootcamp de Java e IA da DIO em parceria com o Itaú.

## ✨ Minha Contribuição e Evolução
Fugindo das tradicionais validações de CRUD, implementei um módulo analítico focado no processamento de dados na camada de aplicação (Clean Architecture), composto por 3 novos Casos de Uso:

1. **Detecção de Anomalias (Outliers):** Usando Média e Desvio Padrão (`DetectAnomaliesUseCase`), o Spring AI agora é capaz de analisar o histórico e alertar o usuário sobre gastos que fogem do seu padrão estatístico.
2. **Projeção de Burn Rate:** O assistente calcula a média diária de gastos (`CalculateBurnRateUseCase`) e projeta o fechamento do mês, avisando se o orçamento está "Acelerado" ou "Estável".
3. **Pipeline de Exportação de Dados:** Um gerador de *Dataset* (`ExportAnalyticsUseCase`) que enriquece as transações com colunas calculadas e exporta em formato CSV para integração com ferramentas de BI.

Para testar, basta acionar a IA (via áudio ou texto) com perguntas como: *"Tem alguma anomalia nos meus gastos?"* ou *"Como está o meu ritmo de despesas?"*.
---
*Abaixo estão as instruções originais de arquitetura e execução fornecidas na base do projeto.*

# 🛠️ Documentação do Projeto Original (Base) 
# DIO Spring Boot - Final Project 05: Spring AI (budgeting)

## Introduction

This final module applies Spring AI in a budgeting API while preserving the same layered architecture used across the track.

The goal is to integrate AI capabilities without bypassing domain and use case boundaries.

## Code Context

The project processes voice commands to create and query financial transactions.

Primary flow:

1. Client uploads an audio file.
2. Audio is transcribed into text.
3. The model selects an application tool/use case.
4. The use case persists or queries transaction data.
5. The final response is converted to audio.

## Project Structure

- `src/main/java/dio/budgeting/domain`
  - Domain model and repository contract.
- `src/main/java/dio/budgeting/application`
  - Use cases used by both REST and AI tool calling.
- `src/main/java/dio/budgeting/infrastructure`
  - HTTP adapters, JPA adapters, and integration glue.

## Module-Specific Topics

### Speech-to-text

- Uses `TranscriptionModel` for audio transcription.
- Model settings are configured in `application.properties`.

### Tool calling

- `ChatClient` registers use-case tools.
- `@Tool` methods expose business capabilities to the model.

### Text-to-speech

- `TextToSpeechModel` produces MP3 output from final text.
- AI endpoint returns generated audio.

## Spring AI Documentation

- Spring AI Reference: https://docs.spring.io/spring-ai/reference/index.html
- ChatModel API: https://docs.spring.io/spring-ai/reference/api/chatmodel.html
- ChatClient API: https://docs.spring.io/spring-ai/reference/api/chatclient.html
- Tools API: https://docs.spring.io/spring-ai/reference/api/tools.html
- Audio Transcriptions API: https://docs.spring.io/spring-ai/reference/api/audio/transcriptions.html
- Audio Speech API: https://docs.spring.io/spring-ai/reference/api/audio/speech.html

## Shared Architecture References

Common architecture concepts are documented in the root README:

- [DDD layers](../README.md#ddd-layered-architecture)
- [Class vs record](../README.md#java-class-vs-java-record-in-domain-modeling)
- [Strong typed identifiers](../README.md#strong-typed-identifiers)
- [Repository pattern](../README.md#repository-pattern)
- [Use cases and Clean Architecture](../README.md#use-cases-and-clean-architecture)
- [Docker Compose support](../README.md#docker-compose-support-in-development)

## How to Run

Set your OpenAI API key:

```bash
export OPENAI_API_KEY="your_api_key_here"
```

Run the application and tests:

```bash
./gradlew bootRun
./gradlew test
```

## Notes

- Educational final project focused on AI plus architectural discipline.
- External provider integration tests may require active credentials.