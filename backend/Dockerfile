FROM gradle:7.5-jdk18
WORKDIR /api
COPY . /api

RUN gradle --no-daemon --warning-mode all --console=plain getDeps

EXPOSE 8080
CMD ["sh", "start.sh"]
