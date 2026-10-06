# Validación funcional de ModaApp

Revisión realizada contra la plantilla Scrum P2 de 4 sprints.

## Sprint 1 · 5 puntos

| Historia | Validación | Resultado |
|---|---|---|
| HU-01 | Campos obligatorios, contraseña oculta, `admin / 1234`, credenciales incorrectas y acceso libre del cliente | Cumple |
| HU-02 | Menú con Ropa, Pedidos, Clientes, Reportes y Salir; navegación separada para cliente | Cumple |
| HU-03 | Nombre ModaApp, paleta rosa/negro, icono y textos de layouts en `strings.xml` | Cumple |

## Sprint 2 · 16 puntos

| Historia | Validación | Resultado |
|---|---|---|
| HU-04 | `modaapp.db`, usuario ADMIN precargado, consulta parametrizada, nombre y rol visibles | Cumple |
| HU-05 | Categorías/tallas, foto copiada a `filesDir`, validaciones y listado con miniatura/stock | Cumple |
| HU-06 | Grilla de dos columnas, chips por categoría y exclusión de prendas sin stock | Cumple |

## Sprint 3 · 16 puntos

| Historia | Validación | Resultado |
|---|---|---|
| HU-07 | Edición, foto opcional, borrado protegido por FK y búsqueda por modelo/marca/color | Cumple |
| HU-08 | Cantidad limitada por stock, contador, subtotales, total, quitar por pulsación larga y estado vacío | Cumple |
| HU-09 | Teléfono de 9 dígitos, cliente existente/nuevo y pedido maestro-detalle transaccional | Cumple |

## Sprint 4 · 18 puntos

| Historia | Validación | Resultado |
|---|---|---|
| HU-10 | Mensajes al cliente y tienda con pedido, prendas, total y estado; control de WhatsApp ausente | Cumple |
| HU-11 | Filtros Pendientes/Atendidos, detalle con fotos y atención transaccional con control de stock | Cumple |
| HU-12 | Venta mensual atendida, pendientes, stock resaltado y clientes con número de pedidos | Cumple |
| HU-13 | Sesión persistente, cierre de sesión y APK release firmado | Cumple |

## Pruebas ejecutadas

- `assembleDebug` y pruebas unitarias: correctas.
- Pruebas instrumentadas en Pixel 8 Pro: 5/5 correctas.
- Verificación visual: título, navegación y acciones quedan debajo de la cámara y barra de estado; acciones inferiores quedan sobre la barra de navegación.
- Verificación SQLite: login parametrizado, categorías, catálogo, transacción de pedido, descuento de stock, rechazo por stock insuficiente, clientes, reportes y sesión.
- La apertura de WhatsApp conserva la intervención obligatoria del usuario para tocar «Enviar», tal como indica el alcance.
