package com.eslam.jittip.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.eslam.jittip.R








@Composable
fun TitleCard(total:Double = 0.00) {
    Card(shape = RoundedCornerShape(8.dp), modifier =
    Modifier
        .padding(all = 15.dp)
        .fillMaxWidth()
        .height(150.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFE9D7F7) )){
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()) {

            CalculationResult(){
                "${"%.2f".format(total)} $"
            }
        }
    }


}
@Composable
fun CalculationResult(result:()->String) {

    Text(
        text = "Total per Person", color = Color.Black,fontSize= TextUnit(18F, TextUnitType.Sp) ,
        fontStyle = FontStyle.Normal, fontWeight = FontWeight.Bold,
    )

    Text(
        text = result.invoke(), color = Color.Black,fontSize= TextUnit(25F, TextUnitType.Sp) ,
        fontStyle = FontStyle.Normal, fontWeight = FontWeight.ExtraBold,
    )

}







@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculationCard(total: MutableDoubleState) {
    Card(shape = RoundedCornerShape(8.dp), modifier =
    Modifier
        .padding(all = 10.dp)
        .fillMaxWidth()
        .height(300.dp)
        // .background(shape = RoundedCornerShape(8.dp), color = Color.White)
        ,
        colors = CardDefaults.cardColors(containerColor = Color.White ), border = BorderStroke(1.dp,
            Color.Gray)
    ) {
        val Bill = remember {
            mutableStateOf("")
        }

        val persons = remember {
            mutableIntStateOf(1)
        }

        val tip = remember {
            mutableDoubleStateOf(0.0)
        }

        val tipPercent = remember {
            mutableFloatStateOf(1f)
        }

        tip.doubleValue = (if (Bill.value.isEmpty()) 0.0 else Bill.value.toDouble())*tipPercent.floatValue/100
        total.doubleValue = ((if (Bill.value.isEmpty()) 0.0 else Bill.value.toDouble())+tip.doubleValue)/persons.intValue
        Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()) {
            CalculationField(Bill)
            SplitRow(persons)
            TipValue(tip)
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(text =  "%.0f".format(tipPercent.floatValue)+"%" )
            Slider(value = tipPercent.floatValue, onValueChange ={
                tipPercent.floatValue =it
            }, steps = 10, valueRange = 0f..100f, colors = SliderDefaults.colors(thumbColor = MaterialTheme.colorScheme.primary) )



        }

    }
}
@Composable
fun SplitRow(text2: MutableState<Int>)
{

    Row(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(top = 5.dp, start = 8.dp, end = 20.dp), verticalAlignment = Alignment.CenterVertically,
    ) {

        Text(text = "Split", textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.width(150.dp))
        Card(shape = RoundedCornerShape(50.dp), modifier = Modifier.size(40.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Image(imageVector = Icons.Default.Remove, contentDescription ="",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxHeight()
                    .size(50.dp)
                    .clickable {

                        text2.value = if (text2.value==1) 1 else text2.value-1
                    })
        }

        Text(text = text2.value.toString(), modifier = Modifier.padding(start = 5.dp, end = 5.dp) )
        Card(shape = RoundedCornerShape(50.dp), modifier = Modifier.size(40.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {

            Image(painter = painterResource(id = R.drawable.baseline_add_24), contentDescription ="",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxHeight()
                    .size(50.dp)
                    .clickable {
                        text2.value++
                    })
        }
    }


}

@Composable
fun TipValue(tip: MutableState<Double>)
{
    Row(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(top = 15.dp, start = 8.dp, end = 20.dp), verticalAlignment = Alignment.CenterVertically,
    ) {

        Text(text = "Tip", textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.width(220.dp))
        Text(text =  "$ ${"%.1f".format(tip.value)}")

    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun CalculationField(text: MutableState<String>)
{
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(value = text.value, onValueChange ={
            textFieldValue ->  text.value = textFieldValue
    }, modifier = Modifier
        .padding(all = 15.dp)
        .fillMaxWidth(), label = { Text(text = "Enter Bill") }, leadingIcon = {
        Image(painter = painterResource(id = R.drawable.baseline_attach_money_24), contentDescription ="",
        )
    }, colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color.Black), singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Decimal), keyboardActions =  KeyboardActions(
            onDone = {keyboardController?.hide()}) )
}
