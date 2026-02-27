@echo off

cd %~dp0..

echo ============================================
echo      LIMPANDO AMBIENTE DOCKER
echo ============================================

echo [1/2] Removendo containers e volumes do projeto...
docker compose -p backend down -v
docker compose -f docker-compose.postgres.yml down -v

echo [2/2] Executando limpeza profunda (Prune)...
docker system prune -a --volumes -f

echo.
echo [OK] Limpeza concluida.
echo.
goto :EOF