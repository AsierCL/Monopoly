# GIT FLOW

## Funcionalidades

#### Traballar nunha nova funcionalidad

    git checkout develop
    git checkout -b feature/nombre_da_funcionalidad

#### Facer na tua rama cambios

    git add .
    git commit -m "tipo_de_cambio: Os cambios que fixeches"
        (Os tipos de cambios son feature, fix, style, perf, revert)
    git pull --rebase
    git push

#### Terminar funcionalidad

    git checkout develop
    git pull
    git merge feature/nombre_da_funcionalidad
    git branch -D feature/nombre_da_funcionalidad
    git push origin --delete feature/nombre_da_funcionalidad

Link: [https://www.atlassian.com/es/git/tutorials/comparing-workflows/gitflow-workflow]