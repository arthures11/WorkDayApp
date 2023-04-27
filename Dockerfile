#FROM mcr.microsoft.com/windows/nanoserver:ltsc2019
#FROM ghcr.io/graalvm/graalvm-ce:22.3.1
#FROM ghcr.io/graalvm/native-image:ol9-java17-22
#FROM ghcr.io/graalvm/jdk:ol9-java17-22
FROM ghcr.io/graalvm/jdk:ol9-java17-22
#FROM ghcr.io/graalvm/jdk:ol8-java17
#FROM gcr.io/distroless/static
#FROM openjdk:17-alpine
#FROM alpine:latest
COPY target/workdayapp /app/app.exe
#COPY --chown=1000:1000 dlls/vcruntime140_1.dll app/vcruntime140_1.dll
#COPY --chown=1000:1000 dlls/vcruntime140.dll app/vcruntime140.dll
#ADD  dlls/* app/
#ADD  ./ projekt/
EXPOSE 8080
#RUN chmod 777 /app/app.exe
#RUN chmod 777 /vcruntime140.dll
#RUN chmod 777 /vcruntime140_1.dll
#RUN vc.exe /quiet /install
#CMD ["ls","app","-l"]
#WORKDIR app
ENTRYPOINT ["/app/app.exe"]
#ENTRYPOINT ["tail", "-f", "/dev/null"]