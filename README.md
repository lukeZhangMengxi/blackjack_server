# blackjack_server
Feature list:
- Player API: player signup and login
    - GET /player/{playerId}
    - POST /player/create?email={}&password={}&displayName={}
    - POST /player/login?email={}&password={}

<br/>

- Game API: single player against robot dealer
    - POST /game/start?={}
    - POST /game/{gameId}/bet?playerId={}&bet={}
    - POST /game/{gameId}/hit?playerId={}
    - POST /game/{gameId}/stand?playerId={}
    - GET /game/{gameId}/status?playerId={}
    - GET /game/{gameId}/result?playerId={}
