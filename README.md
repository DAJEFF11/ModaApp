# ModaApp

Aplicación Android nativa para Boutique Moda Urbana, desarrollada con Kotlin, layouts XML, Material Components y SQLite.

## Estado del proyecto

- Sprint 1: login, menú, navegación e identidad visual. ✅
- Sprint 2: SQLite, registro con foto y catálogo por categoría. ✅
- Sprint 3: CRUD, carrito y registro transaccional de pedidos. ✅
- Sprint 4: WhatsApp, atención, reportes, clientes, sesión y APK. ✅

La rama `Sprint-4` contiene la entrega final y conserva todo lo realizado en los sprints anteriores.

## Acceso

- Administrador: `admin` / `1234`
- Cliente: acceso libre desde «Ver catálogo (cliente)».

## Funcionalidades

- Base local `modaapp.db` versión 2 con migración que conserva catálogo y usuarios.
- Registro de prendas con foto copiada al almacenamiento interno.
- Catálogo filtrable, carrito con control de stock y pedido por teléfono.
- Registro de cliente nuevo y pedido maestro-detalle dentro de una transacción.
- Mensajes preparados para WhatsApp del cliente y de la tienda.
- Atención transaccional del pedido con validación y descuento de stock.
- Reporte mensual, stock por prenda y clientes con cantidad de pedidos.
- Sesión del administrador recordada hasta pulsar «Salir».

## Compilación

```powershell
.\gradlew.bat assembleDebug testDebugUnitTest
```

El APK final firmado se encuentra en `releases/ModaApp-v1.0.apk`.
