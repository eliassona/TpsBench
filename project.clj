(defproject sctp_tcp_test "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/tools.nrepl "0.2.11"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 #_[local/sctp "1.0.0"]]
;  :repositories {"project" "file:maven_repository"}
  :java-source-paths ["src/main/java"]
  :main sctp-tcp-test.core
  :profiles {:dev {:repositories {"project" "file:maven_repository"}
                   :dependencies [[local/sctp "1.0.0"]],
                   }}
  )
