(ns bblife.flags
  (:require [bblgum.core :as b]
            [babashka.cli :as cli]
            [babashka.process :refer [shell]]))

(defn show-help
  [spec]
  (cli/format-opts (merge spec {:order (vec (keys (:spec spec)))})))

(def cli-spec
  {:spec
   {:edit {:coerce :boolean
           :desc "Open a selected file using the editor defined in $EDITOR"
           :alias :e}
    :pretty {:coerce :boolean
             :desc "Display the contents of a file with improved formatting"
             :alias :p}
    :archive {:coerce :boolean
              :desc "Move the selected file to ~/life/archive/<dir> and rename it with the current date"
              :alias :a}
    :help {:coerce :boolean
           :desc "Show this help message with available options"
           :alias :h}
    :flag {:coerce :boolean
           :desc "A placeholder flag for testing purposes"}}})
