# sctp_tcp_test

A program designed to benchmark TCP and SCTP transport protocol.


## Installation

This is a leinigen project so it must be installed first. (leinigen.org)

## Usage

```clojure
(use 'sctp-tcp-test.core)

(bench :tcp)
=>
{:server-delay-ms 0,
 :disable-fragments false,
 :no-delay true,
 :nr-of-messages 1000000,
 :port 9431,
 :duration-ms 2834,
 :host "localhost",
 :message-size 1800,
 :tps 352858,
 :rec-buffer 100000,
 :rec-traffic false,
 :client-delay-ms 0,
 :send-buffer 100000}

(bench :tcp :port 9876)
=>
{:server-delay-ms 0,
 :disable-fragments false,
 :no-delay true,
 :nr-of-messages 1000000,
 :port 9876,
 :duration-ms 2872,
 :host "localhost",
 :message-size 1800,
 :tps 348189,
 :rec-buffer 100000,
 :rec-traffic false,
 :client-delay-ms 0,
 :send-buffer 100000}
 ```

;to see supported options and their default values

```clojure
default-options
=>
{:server-delay-ms 0,
 :disable-fragments false,
 :no-delay true,
 :nr-of-messages 1000000,
 :port 9431,
 :host "localhost",
 :message-size 1800,
 :rec-buffer 100000,
 :rec-traffic false,
 :client-delay-ms 0,
 :send-buffer 100000}
```



FIXME

## License

Copyright © 2015 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
