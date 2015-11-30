(defproject diameter "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0-alpha4"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [aleph "0.3.2"]
                 ;[aleph "0.4.0-alpha9"]
                 [instaparse "1.3.0"]
                 [defun "0.1.0"]
                 [uncomplicate/fluokitten "0.3.0"]
                 [com.taoensso/timbre "3.4.0"]
                 [automat "0.1.3"]
                 [criterium "0.4.3"]
                 ]

  :java-source-paths ["java/src"
                      ]
  :source-paths ["src" 
                 ]
  :repl-options 
  {
  ; :port 4555
   }
  :profiles {:dev {:dependencies [[midje "1.5.1"]]}}
  :documentation
  {:files {
           "Diameter"             
           {:input "./test/diameter/diametertest.clj"   
            :title "Diameter"             
            :sub-title ""     
            :author "Anders Eliasson"
            :email  "anders.eliasson@digitalroute.com"}}}
  :plugins [[lein-gorilla "0.3.3"]]
  )
