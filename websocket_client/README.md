# websocket_client for blackjack_server
Run the browser client with different port numbers using:
```
node host.js ${PORT}
```

and visit the client page on a browser with the URL, http://localhost:${PORT}.
We should expect that multiple browser clients could subscribe to the same broker.

<br/><br/>

## Description
Currently this is a prototype websocket browser client,
subscribing [the example broker](https://github.com/spring-guides/gs-messaging-stomp-websocket).

Please note we need to add 
```
.setAllowedOrigins("*")
```
to the registered Stomp endpoint "/gs-guide-websocket" on `WebSocketConfig`.
