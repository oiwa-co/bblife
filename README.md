# Dependencies: 
- Gum: https://github.com/charmbracelet/gum

# BB Life

BB Life is a command-line tool written in Clojure that helps you manage and organize your `~/life` directory and its associated files. The tool handles the following tasks:

- **Create and verify** the default directory and file structure in `~/life`:
  - **Files:** `calendar.txt`, `inbox.txt`, `index.md`, `notes.txt`, `projects.txt`, `someday.txt`, `todo.txt`.
  - **Archive directory:** Located in `~/life/archive` with subdirectories based on the name of each file.

- **Provide functionalities to:**
  - **Open files** using the editor defined in the `$EDITOR` environment variable (`--edit` or `-e`).
  - **Display file contents** in a paginated view with enhanced formatting (`--pretty` or `-p`).
  - **Archive files:** Move the selected file to `~/life/archive/<category>` and rename it by prefixing the current date (`--archive` or `-a`).
  - **Display a help message** (`--help` or `-h`).

---

## Installation

Requires Babashka and Clojure dependencies.

1. **Clone the repository:**

```bash
git clone https://github.com/oiwa-co/bblife.git
```
2. **Access the directory:**

```sh
cd bblife
```

3. **Run the alias to launch the application:**

```sh
bb -m bblife.core
```

## Usage

Run the program without any flags to check the status of your ~/life directory. Available options include:

    --edit (-e): Opens a file using the editor configured in $EDITOR.

    --pretty (-p): Displays the content of a file with pagination and formatting.

    --archive (-a): Archives the selected file by moving it to ~/life/archive/<category> and renaming it with the current date.

    --help (-h): Displays this help message. 
