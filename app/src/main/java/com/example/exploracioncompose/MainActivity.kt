package com.example.exploracioncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.ui.window.Dialog

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
        val grupo = intent.getStringExtra("grupo") ?: "contenedores"
        setContent { MaterialTheme { Catalogo(grupo) } }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Catalogo(grupo: String) {
    val grupos: Map<String, List<Pair<String, @Composable () -> Unit>>> = mapOf(
        "contenedores" to listOf("LazyColumn" to { EjemploLazyColumn() }, "LazyRow" to { EjemploLazyRow() },
            "Grid" to { EjemploGrid() }, "ConstraintLayout" to { EjemploConstraintLayout() },
            "Scaffold" to { EjemploScaffold() }, "Surface" to { EjemploSurface() },
            "Chip" to { EjemploChip() }, "BackdropScaffold" to { EjemploBackdrop() },
            "FlowRow" to { EjemploFlowRow() }, "FlowColumn" to { EjemploFlowColumn() }),
        "controles1" to listOf("AlertDialog" to { EjemploAlertDialog() }, "Card" to { EjemploCard() },
            "Checkbox" to { EjemploCheckbox() }, "FloatingActionButton" to { EjemploFab() },
            "Icon" to { EjemploIcon() }, "Image" to { EjemploImage() }, "ProgressBar" to { EjemploProgress() },
            "RadioButton" to { EjemploRadio() }, "Slider" to { EjemploSlider() }, "Spacer" to { EjemploSpacer() },
            "Switch" to { EjemploSwitch() }, "TopAppBar" to { EjemploTopBar() }),
        "controles2" to listOf("BottomNavigation" to { EjemploBottomNavigation() }, "Dialog" to { EjemploDialog() },
            "Divider" to { EjemploDivider() }, "DropDownMenu" to { EjemploMenu() },
            "LazyVerticalGrid" to { EjemploLazyGrid() }, "NavigationRail" to { EjemploNavigationRail() },
            "OutlinedTextField" to { EjemploTextField() }, "Pager" to { EjemploPager() },
            "Snackbar" to { EjemploSnackbar() }, "TabRow" to { EjemploTabRow() }, "Tooltip" to { EjemploTooltip() })
    )
    val ejemplos = grupos[grupo] ?: grupos.getValue("contenedores")
    LazyColumn(Modifier.fillMaxSize().statusBarsPadding().padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        item { Text("Exploración de componentes", style = MaterialTheme.typography.titleLarge) }
        items(ejemplos.size) { i ->
            val (nombre, contenido) = ejemplos[i]
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(10.dp)) {
                    Text(nombre, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(4.dp))
                    contenido()
                }
            }
        }
    }
}

@Composable fun EjemploLazyColumn() { LazyColumn(Modifier.height(80.dp)) { items(3) { Text("Elemento ${it + 1}") } } }
@Composable fun EjemploLazyRow() { LazyRow { items(4) { Text("Elemento ${it + 1}", Modifier.padding(6.dp)) } } }
@Composable fun EjemploGrid() { Column { repeat(2) { fila -> Row { repeat(3) { col -> Text("${fila * 3 + col + 1}", Modifier.padding(8.dp)) } } } } }
@Composable fun EjemploConstraintLayout() { ConstraintLayout(Modifier.fillMaxWidth().height(45.dp)) { val (a,b) = createRefs(); Text("Inicio", Modifier.constrainAs(a) { start.linkTo(parent.start); top.linkTo(parent.top) }); Text("Fin", Modifier.constrainAs(b) { end.linkTo(parent.end); top.linkTo(parent.top) }) } }
@Composable fun EjemploScaffold() { Scaffold(Modifier.height(90.dp), topBar = { Text("Barra superior") }, bottomBar = { Text("Barra inferior") }) { padding -> Text("Contenido", Modifier.padding(padding)) } }
@Composable fun EjemploSurface() { Surface(color = Color(0xFFD6E4FF)) { Text("Superficie", Modifier.padding(12.dp)) } }
@OptIn(ExperimentalMaterial3Api::class) @Composable fun EjemploChip() { AssistChip(onClick = {}, label = { Text("Opción") }) }
@OptIn(androidx.compose.material.ExperimentalMaterialApi::class) @Composable fun EjemploBackdrop() {
    androidx.compose.material.BackdropScaffold(scaffoldState = androidx.compose.material.rememberBackdropScaffoldState(androidx.compose.material.BackdropValue.Concealed),
        appBar = { Text("Menú", Modifier.padding(8.dp)) }, backLayerContent = { Text("Opciones", Modifier.padding(8.dp)) },
        frontLayerContent = { Text("Contenido frontal", Modifier.padding(8.dp)) }, modifier = Modifier.height(130.dp))
}
@OptIn(ExperimentalLayoutApi::class) @Composable fun EjemploFlowRow() { FlowRow(Modifier.width(200.dp)) { repeat(5) { Text("Item ${it + 1}", Modifier.padding(6.dp)) } } }
@OptIn(ExperimentalLayoutApi::class) @Composable fun EjemploFlowColumn() { FlowColumn(Modifier.height(80.dp), maxItemsInEachColumn = 3) { repeat(6) { Text("Item ${it + 1}", Modifier.padding(3.dp)) } } }
@Composable fun EjemploAlertDialog() { var abierto by remember { mutableStateOf(false) }; Button(onClick = { abierto = true }) { Text("Abrir aviso") }; if (abierto) AlertDialog(onDismissRequest = { abierto = false }, title = { Text("Aviso") }, text = { Text("¿Continuar?") }, confirmButton = { TextButton(onClick = { abierto = false }) { Text("Aceptar") } }) }
@Composable fun EjemploCard() { Card { Text("Contenido de la tarjeta", Modifier.padding(12.dp)) } }
@Composable fun EjemploCheckbox() { var marcado by remember { mutableStateOf(true) }; Checkbox(checked = marcado, onCheckedChange = { marcado = it }) }
@Composable fun EjemploFab() { FloatingActionButton(onClick = {}) { Icon(Icons.Default.Add, contentDescription = "Agregar") } }
@Composable fun EjemploIcon() { Icon(Icons.Default.Favorite, contentDescription = "Favorito") }
@Composable fun EjemploImage() { Image(painterResource(R.drawable.tecsup), contentDescription = "Tecsup", modifier = Modifier.size(85.dp)) }
@Composable fun EjemploProgress() { LinearProgressIndicator(progress = { .6f }, modifier = Modifier.fillMaxWidth()) }
@Composable fun EjemploRadio() { var seleccionado by remember { mutableStateOf(true) }; RadioButton(selected = seleccionado, onClick = { seleccionado = !seleccionado }) }
@Composable fun EjemploSlider() { var valor by remember { mutableFloatStateOf(.4f) }; Slider(value = valor, onValueChange = { valor = it }) }
@Composable fun EjemploSpacer() { Row { Text("Uno"); Spacer(Modifier.width(35.dp)); Text("Dos") } }
@Composable fun EjemploSwitch() { var activo by remember { mutableStateOf(true) }; Switch(checked = activo, onCheckedChange = { activo = it }) }
@OptIn(ExperimentalMaterial3Api::class) @Composable fun EjemploTopBar() { TopAppBar(title = { Text("Inicio") }) }
@Composable fun EjemploBottomNavigation() { NavigationBar { NavigationBarItem(selected = true, onClick = {}, icon = { Icon(Icons.Default.Home, "Inicio") }, label = { Text("Inicio") }); NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Favorite, "Favoritos") }, label = { Text("Favoritos") }) } }
@Composable fun EjemploDialog() { var abierto by remember { mutableStateOf(false) }; Button(onClick = { abierto = true }) { Text("Abrir diálogo") }; if (abierto) Dialog(onDismissRequest = { abierto = false }) { Card { Text("Contenido del diálogo", Modifier.padding(24.dp)) } } }
@Composable fun EjemploDivider() { Column { Text("Primero"); HorizontalDivider(); Text("Segundo") } }
@Composable fun EjemploMenu() { var abierto by remember { mutableStateOf(false) }; Box { TextButton(onClick = { abierto = true }) { Text("Opciones") }; DropdownMenu(expanded = abierto, onDismissRequest = { abierto = false }) { DropdownMenuItem(text = { Text("Editar") }, onClick = { abierto = false }); DropdownMenuItem(text = { Text("Eliminar") }, onClick = { abierto = false }) } } }
@Composable fun EjemploLazyGrid() { LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.height(80.dp)) { items(6) { Text("${it + 1}", Modifier.padding(8.dp)) } } }
@Composable fun EjemploNavigationRail() { NavigationRail(Modifier.height(150.dp)) { NavigationRailItem(selected = true, onClick = {}, icon = { Icon(Icons.Default.Home, "Inicio") }, label = { Text("Inicio") }) } }
@Composable fun EjemploTextField() { var texto by remember { mutableStateOf("") }; OutlinedTextField(value = texto, onValueChange = { texto = it }, label = { Text("Nombre") }) }
@Composable fun EjemploPager() { val estado = rememberPagerState(pageCount = { 3 }); HorizontalPager(state = estado, modifier = Modifier.fillMaxWidth().height(60.dp)) { Text("Página ${it + 1}", Modifier.padding(12.dp)) } }
@Composable fun EjemploSnackbar() { Snackbar { Text("Cambios guardados") } }
@Composable fun EjemploTabRow() { var tab by remember { mutableIntStateOf(0) }; PrimaryTabRow(selectedTabIndex = tab) { listOf("Inicio", "Perfil").forEachIndexed { i, title -> Tab(selected = tab == i, onClick = { tab = i }, text = { Text(title) }) } } }
@OptIn(ExperimentalMaterial3Api::class) @Composable fun EjemploTooltip() { TooltipBox(positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(), tooltip = { PlainTooltip { Text("Información") } }, state = rememberTooltipState()) { Icon(Icons.Default.Favorite, contentDescription = "Información") } }

@Preview(showBackground = true) @Composable fun PreviewContenedores() { Catalogo("contenedores") }
@Preview(showBackground = true) @Composable fun PreviewControles1() { Catalogo("controles1") }
@Preview(showBackground = true) @Composable fun PreviewControles2() { Catalogo("controles2") }
@Preview(showBackground = true) @Composable fun PreviewLazyColumn() { EjemploLazyColumn() }
@Preview(showBackground = true) @Composable fun PreviewLazyRow() { EjemploLazyRow() }
@Preview(showBackground = true) @Composable fun PreviewGrid() { EjemploGrid() }
@Preview(showBackground = true) @Composable fun PreviewConstraintLayout() { EjemploConstraintLayout() }
@Preview(showBackground = true) @Composable fun PreviewScaffold() { EjemploScaffold() }
@Preview(showBackground = true) @Composable fun PreviewSurface() { EjemploSurface() }
@Preview(showBackground = true) @Composable fun PreviewChip() { EjemploChip() }
@Preview(showBackground = true) @Composable fun PreviewBackdrop() { EjemploBackdrop() }
@Preview(showBackground = true) @Composable fun PreviewFlowRow() { EjemploFlowRow() }
@Preview(showBackground = true) @Composable fun PreviewFlowColumn() { EjemploFlowColumn() }
@Preview(showBackground = true) @Composable fun PreviewAlertDialog() { EjemploAlertDialog() }
@Preview(showBackground = true) @Composable fun PreviewCard() { EjemploCard() }
@Preview(showBackground = true) @Composable fun PreviewCheckbox() { EjemploCheckbox() }
@Preview(showBackground = true) @Composable fun PreviewFab() { EjemploFab() }
@Preview(showBackground = true) @Composable fun PreviewIcon() { EjemploIcon() }
@Preview(showBackground = true) @Composable fun PreviewImage() { EjemploImage() }
@Preview(showBackground = true) @Composable fun PreviewProgress() { EjemploProgress() }
@Preview(showBackground = true) @Composable fun PreviewRadio() { EjemploRadio() }
@Preview(showBackground = true) @Composable fun PreviewSlider() { EjemploSlider() }
@Preview(showBackground = true) @Composable fun PreviewSpacer() { EjemploSpacer() }
@Preview(showBackground = true) @Composable fun PreviewSwitch() { EjemploSwitch() }
@Preview(showBackground = true) @Composable fun PreviewTopBar() { EjemploTopBar() }
@Preview(showBackground = true) @Composable fun PreviewBottomNavigation() { EjemploBottomNavigation() }
@Preview(showBackground = true) @Composable fun PreviewDialog() { EjemploDialog() }
@Preview(showBackground = true) @Composable fun PreviewDivider() { EjemploDivider() }
@Preview(showBackground = true) @Composable fun PreviewMenu() { EjemploMenu() }
@Preview(showBackground = true) @Composable fun PreviewLazyGrid() { EjemploLazyGrid() }
@Preview(showBackground = true) @Composable fun PreviewNavigationRail() { EjemploNavigationRail() }
@Preview(showBackground = true) @Composable fun PreviewTextField() { EjemploTextField() }
@Preview(showBackground = true) @Composable fun PreviewPager() { EjemploPager() }
@Preview(showBackground = true) @Composable fun PreviewSnackbar() { EjemploSnackbar() }
@Preview(showBackground = true) @Composable fun PreviewTabRow() { EjemploTabRow() }
@Preview(showBackground = true) @Composable fun PreviewTooltip() { EjemploTooltip() }
