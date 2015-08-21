Estrutura:

Fontes -> src/main/java
Testes -> src/test/java
Jar executavel -> target/jfx/app/hermes-qrt-interface-sw-jfx
Jar de API -> target/hermes-qrt-interface-sw-api
Doxygen -> target/doc

- Para gerar o jar executável: goal jfx:far do Maven
- Para gerar o jar de API: mvn package -Papi
- Para gerar o Doxygen: goal doxygen:report