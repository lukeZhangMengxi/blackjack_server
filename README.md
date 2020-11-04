# blackjack_server
Feature list:
- Player API: player signup and login
    - GET /player/{playerId}
    - POST /player/create?email={}&password={}&displayName={}
    - POST /player/login?email={}&password={}

<br/>

- Game API: single player against robot dealer
    - Notes:
        - All the endpoints below require `jwt` header for authorization; only the player himself/herself has the previlige to their game actions below.
    - POST /game/start?={}
    - POST /game/{gameId}/bet?playerId={}&bet={}
    - POST /game/{gameId}/hit?playerId={}
    - POST /game/{gameId}/stand?playerId={}
    - GET /game/{gameId}/status?playerId={}
    - GET /game/{gameId}/result?playerId={}
