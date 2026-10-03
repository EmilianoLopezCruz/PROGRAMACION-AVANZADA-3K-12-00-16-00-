# Sistema de Apartado de Laboratorios – Facultad de Ingeniería Tampico (UAT)

Prototipo en JavaFX con datos de ejemplo en memoria. No hay perfiles ni inicio de sesión: la aplicación la usa directamente quien administra los laboratorios (secretaría o administración).
Lo que se agrega o elimina (edificios, laboratorios, maestros, apartados, anotaciones) dura mientras la aplicación está abierta; al cerrarla se restauran los datos de ejemplo.

## Requisitos
- JDK 21
- JavaFX SDK 21 (https://gluonhq.com/products/javafx/)

## Ejecutar en Eclipse
1. File > Import > Existing Projects into Workspace > selecciona la carpeta `ApartadosLabFIT`.
2. Agrega JavaFX a la ruta de módulos (Build Path > Libraries > Modulepath > Add External JARs: todos los `.jar` de `javafx-sdk-21/lib`).
3. Run Configurations > Arguments > VM arguments:
   `--module-path "RUTA/javafx-sdk-21/lib" --add-modules javafx.controls,javafx.fxml`
4. Ejecuta `application.Main`.

## Flujo
1. Al abrir se elige el edificio (Nave de Industrial, Nave de Civil o los que se agreguen con **Agregar edificio**).
2. Todas las vistas muestran los laboratorios y apartados del edificio elegido; se puede cambiar con la opción 9.

## Qué se puede hacer
- **Apartar laboratorio**: elegir docente, laboratorio, fecha, horario, materia, grupo y alumnos. El sistema rechaza fechas pasadas, horarios fuera de 07:00–21:00, más alumnos que la capacidad, laboratorios en mantenimiento, traslapes con otro apartado del laboratorio y docentes con otro apartado a la misma hora.
- **Apartados**: buscar (folio, laboratorio, materia, grupo, docente), filtrar por estado, cancelar, guardar una anotación y marcar «el maestro no llegó» (solo después de la hora de inicio). Un apartado cancelado o con «no llegó» libera el horario.
- **Disponibilidad**: cuadrícula hora por hora del día elegido, calculada con los apartados reales y el mantenimiento. Al pasar el cursor sobre una celda ocupada se ve quién la apartó.
- **Laboratorios**: agregar, editar, eliminar (si no tiene apartados pendientes), poner en mantenimiento con motivo y fecha de reapertura, y guardar anotaciones. Si hay apartados confirmados dentro del mantenimiento se avisa y se cancelan. Al llegar la fecha de reapertura el laboratorio vuelve a quedar disponible solo.
- **Maestros**: agregar y eliminar docentes (no se puede eliminar a uno con apartados confirmados pendientes). Un maestro es solo un dato para elegirlo al apartar, no un usuario del sistema.
- **Reportes**: apartados, horas y alumnos por laboratorio en el periodo elegido, con gráfica, y exportación a un archivo `.txt`.
- **Edificios**: agregar desde la pantalla de selección.

## Estructura del código (`src/`)
| Paquete | Contenido |
|---------|-----------|
| `application` | `Main`: arranque y cambio de pantalla |
| `Vista` | Archivos FXML y `estilos.css` |
| `Vistacontrolador` | Controladores, modelo (`Edificio`, `Laboratorio`, `Maestro`, `Apartado`), `Catalogo` (datos y reglas) y `Util` |

Los controladores solo consultan a `Catalogo`; para conectar una base de datos basta con reemplazar el interior de `Catalogo` por DAO o repositorios.

## Navegación con teclado
| Tecla | Opción |
|-------|--------|
| 1 | Inicio |
| 2 | Apartar laboratorio |
| 3 | Apartados |
| 4 | Disponibilidad |
| 5 | Laboratorios |
| 6 | Maestros |
| 7 | Reportes |
| 8 | ¿Cómo funciona? |
| 9 | Cambiar de edificio |

↑ ↓ mueven el foco, → entra a la vista, Esc regresa al menú y Enter abre la opción enfocada.
En la pantalla de edificios, las teclas 1 a 9 eligen el edificio, A agrega uno nuevo y H abre la ayuda.
Al pasar el cursor sobre una opción del menú lateral aparece una bandera azul con cruz blanca.
