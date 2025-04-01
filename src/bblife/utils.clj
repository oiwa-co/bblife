(ns bblife.utils
  (:require [bblgum.core :as b]
            [babashka.fs :as fs]
            [clojure.string :as str]
            [babashka.process :refer [shell sh]]))

;; Base directory for life
(def path (str (fs/expand-home "~/life")))

;; Directory for archived files
(def archive-path (str (fs/expand-home "~/life/archive")))

;; Opens a TUI file selector and returns the selected file.
;; Uses `gum` to provide a selection interface.
;; Returns:
;;   - The absolute path of the selected file (as a string).
(defn search-and-parse
  []
  (let [file (b/gum :file [path])]
    (first (:result file))))

;; Opens a selected file using the default system editor.
;; The editor is determined by the `EDITOR` environment variable.
(defn open-with-editor
  []
  (when-let [file (search-and-parse)]
    (let [editor (or (System/getenv "EDITOR"))]
      (shell editor file))))

;; Displays a selected file in a paginated viewer.
;; Uses gum to provide a smooth scrolling interface.
(defn pretty-pager
  []
  (when-let [file (search-and-parse)]
    (b/gum :pager :as :ignored :in (clojure.java.io/input-stream file))))

;; Moves a selected file to the appropriate archive subdirectory.
;; file is renamed with the current date as a prefix.
;; Steps:
;;   1. User selects a file from `~/life`.
;;   2. The file is moved to `~/life/archive/<category>/`.
;;   3. The file is renamed using the format: `YYYY-MM-DD_filename.ext`.
;;   4. A new empty file with the original name is created.
(defn move-to-archive
  []
  (when-let [source (search-and-parse)]
    (let [file-name (fs/file-name source) ;; Extracts the file name
          base-name (fs/strip-ext file-name) ;; Removes the file extension
          target-dir (first (#(str/split % #"\.") (fs/strip-ext file-name)))
          today (str/trim (:out (sh "date" "+%F")))
          target-path (str archive-path "/" target-dir)]
      (fs/move source target-path)
      (fs/move (str target-path "/" file-name) 
               (str target-path "/" today "_" file-name))
      (b/gum :spin ["sleep" "1"] :spinner "line" :title (str "Regenerating " source))
      (fs/create-file source))))   
