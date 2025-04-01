(ns bblife.core
  (:require [babashka.fs :as fs]
            [clojure.string :as str]
            [bblgum.core :as b]))

;; Base directory for `life`
(def life-dir (fs/expand-home "~/life"))

;; Path to the `archive` subdirectory, where archived files will be organized.
(def archive-path (fs/expand-home "~/life/archive"))

;; Creates a directory if it does not exist.
;; Parameters:
;;   - `dir`: Path of the directory to check or create.
;;   - `title`: Status message for the loading animation.
(defn ensure-dir [dir title]
  (when-not (fs/exists? dir)
    ;; Displays a loading indicator before creating the directory.
    (b/gum :spin ["sleep" "2"] :spinner "line" :title title)
    (fs/create-dir dir)))

;; List of default files created in `~/life`
(def life-files '("calendar.txt" "inbox.txt" "index.md" "notes.txt"
                  "projects.txt" "someday.txt" "todo.txt"))

;; Checks and creates the default files in `~/life` if they do not exist.
(defn ensure-files []
  (b/gum :spin ["sleep" "2"] :spinner "line" :title "Checking life files...")
  (doseq [file life-files]
    (let [path (fs/path life-dir file)]
      (when-not (fs/exists? path)
        (fs/create-file path)))))

;; Creates subdirectories inside `~/life/archive` based on the names of `life-files`
;; (without extensions). This allows organizing archived files by category.
(defn ensure-archive-subdirs []
  (b/gum :spin ["sleep" "2"] :spinner "line" :title "Checking archive structure...")
  (doseq [dir-name (map #(first (str/split % #"\.")) life-files)]
    (fs/create-dirs (fs/path archive-path dir-name))))

;; Verifies and sets up the structure of `~/life` and `~/life/archive`.
;; - Creates the main directories if they do not exist.
;; - Creates default files in `~/life` if they do not exist.
;; - Creates subdirectories in `~/life/archive` based on file categories.
(defn check-life []
  (ensure-dir life-dir "Crafting ~/life directory...")
  (ensure-dir archive-path "Crafting ~/life/archive directory...")
  (ensure-files)
  (ensure-archive-subdirs)
  (println "All good"))
