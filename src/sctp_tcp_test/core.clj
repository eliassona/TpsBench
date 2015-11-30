(ns sctp-tcp-test.core
  (:use [clojure.pprint])
  (:require
    [clojure.core.async :refer [thread chan go >! <! >!! <!! go-loop alts! timeout onto-chan pipeline close! sliding-buffer]]
    [clojure.tools.nrepl.server :refer [start-server]]
    [clojure.walk :refer [keywordize-keys]])
  (:import [main.java SctpServer SctpClient TcpServer TcpClient Options])
  (:gen-class))

(defmacro def-options 
  ""
  [type & options]
  `(let [triplets# (partition 3 '~options)]
     (def ~'default-options (into {} (map (fn [[k# v#]] [k# v#]) triplets#)))
     (defn ~'options-of [options-map#]
       (let [~'om (merge ~'default-options options-map#)]
         {
          :map ~'om
          :obj (reify ~type
                 ~@(map (fn [[k _ m]] `(~m [~'_] (~k ~'om))) (partition 3 options))
                 )}))))

(def-options Options
   :send-buffer     100000  sendBuffer,
   :rec-buffer      100000  receiveBuffer,
   :message-size    1800    messageSize,
   :nr-of-messages  1000000 nrOfMessages,
   :rec-traffic     false   recTraffic,
   :port            9431    port,
   :server-delay-ms 0       serverDelayMs,
   :client-delay-ms 0       clientDelayMs
   :no-delay        true    noDelay
   :disable-fragments false disableFragments)


(defn impl-of [protocol options-map]
  (assoc
    (condp = protocol
      :tcp {:client (TcpClient.) :server (TcpServer.)}
      :sctp {:client (SctpClient.) :server (SctpServer.)})
    :options (options-of options-map)))



(defn bench 
  "Benchmark transport protocols on localhost by starting a server and a client. 
   Tcp and sctp are supported"
  [protocol & options]
  (let [{:keys [client server options]} (impl-of protocol (apply hash-map options))
        {:keys [map obj]} options]
    (let [c (go (.execute server obj))]
      (Thread/sleep 1000)
      (go (.execute client obj))
      (merge map (keywordize-keys (into {} (map identity (<!! c))))))))

(defn -main [& args]
  (start-server :port 5555))
