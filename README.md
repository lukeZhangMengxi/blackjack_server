# blackjack_server
Please check out the ReactJS browser GIF animation demo with this service at [blackjack_ui/README.md](https://github.com/lukeZhangMengxi/blackjack_ui#blackjack_ui).

<br/>

- Start an example database Docker container:
    ```
    chmod +x example_database_init.sh
    ./example_database_init.sh
    ```
- Start the blackjack_server:
    ```
    ./gradlew bootRun
    ```

<br/>

## Feature list:
- Player API: player signup and login
    - GET /player/{playerId}
    - POST /player/create?email={}&password={}&displayName={}
    - POST /player/login?email={}&password={}

<br/>

- Game API: single player against robot dealer
    - Note:
        - All the endpoints below require `jwt` header for authorization; only the player himself/herself has the previlige to their game actions below.
    - GET /game/{gameId}/status?playerId={}
    - GET /game/{gameId}/result?playerId={}
    - POST /game/start?={}
    - POST /game/{gameId}/bet?playerId={}&bet={}
    - POST /game/{gameId}/hit?playerId={}
    - POST /game/{gameId}/stand?playerId={}

<br/>

- Multi-player Game API: Multiple players against robot dealer
    - GET /mpgame/list
    - Note:
        - All the endpoints below require `jwt` header for authorization; only the player himself/herself has the previlige to their game actions below.
    - POST /mpgame/create?ownerId={}
    - POST /mpgame/join/{gameId}?playerId={}
    - POST /mpgame/{gameId}/start?playerId={}
    - POST /mpgame/{gameId}/bet?playerId={}&bet={}
    - POST /mpgame/{{gameId}}/hit?playerId={}
    - POST /mpgame/{{gameId}}/stand?playerId={}
