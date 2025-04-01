(ns main
  (:require [bblife.core :as core]
            [bblife.flags :as flags]
            [bblife.utils :as utils]
            [babashka.cli :as cli]))

;; Handle flags or no flags
(defn -main
  [args]
  (let [opts (cli/parse-opts args flags/cli-spec)]
    (cond
      (:help opts) (println (flags/show-help flags/cli-spec))
      (:edit opts) (utils/open-with-editor)
      (:pretty opts) (utils/pretty-pager)
      (:archive opts) (utils/move-to-archive)
      :else (do
              (println "No flag or no valid flag provided, checking vital signs")
              (core/check-life)
              (println "Use --help or -h to see options.")))))

;; Execute the main function with command-line arguments.
(-main *command-line-args*)
