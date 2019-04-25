package ninja.cyplay.com.apilibrary.domain.repositoryModel.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;
import java.util.List;

import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.ShopStatisticsResponse;
import ninja.cyplay.com.apilibrary.models.business.PR_Chart;

/**
 * Created by damien on 30/05/16.
 */
public class PR_GetShopStatisticsDeserializer implements JsonDeserializer<ShopStatisticsResponse>
{
    @Override
    public ShopStatisticsResponse deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException
    {

//        String str = "{  \n" +
//                "   \"results\":[  \n" +
//                "      {  \n" +
//                "         \"title\":\"NOMBRE DE CLIENTS RECRUTÉS\",\n" +
//                "         \"rightAxis\":{  \n" +
//                "            \"label\":{  \n" +
//                "               \"font\":\"HelveticaNeue-Bold\",\n" +
//                "               \"enabled\":true,\n" +
//                "               \"fontSize\":14,\n" +
//                "               \"color\":\"#00000000\"\n" +
//                "            },\n" +
//                "            \"gridLine\":{  \n" +
//                "               \"width\":0.5,\n" +
//                "               \"enabled\":true,\n" +
//                "               \"color\":\"#FFFFFFFF\"\n" +
//                "            },\n" +
//                "            \"position\":\"insideChart\",\n" +
//                "            \"axisLine\":{  \n" +
//                "               \"enabled\":true,\n" +
//                "               \"color\":\"#00000000\",\n" +
//                "               \"width\":0.5\n" +
//                "            }\n" +
//                "         },\n" +
//                "         \"leftAxis\":{  \n" +
//                "            \"label\":{  \n" +
//                "               \"font\":\"HelveticaNeue-Bold\",\n" +
//                "               \"enabled\":false,\n" +
//                "               \"fontSize\":14,\n" +
//                "               \"color\":\"#00000000\"\n" +
//                "            },\n" +
//                "            \"gridLine\":{  \n" +
//                "               \"width\":0.5,\n" +
//                "               \"enabled\":false,\n" +
//                "               \"color\":\"#00000000\"\n" +
//                "            },\n" +
//                "            \"position\":\"outsideChart\",\n" +
//                "            \"axisLine\":{  \n" +
//                "               \"enabled\":false,\n" +
//                "               \"color\":\"#00000000\",\n" +
//                "               \"width\":0.5\n" +
//                "            }\n" +
//                "         },\n" +
//                "         \"drawValueAboveBar\":true,\n" +
//                "         \"interactionEnabled\":false,\n" +
//                "         \"gridBackgroundColor\":\"#00000000\",\n" +
//                "         \"showsFilterButtons\":false,\n" +
//                "         \"type\":\"HorizontalBarChart\",\n" +
//                "         \"detailedDescription\":\"Detailed description\",\n" +
//                "         \"xAxis\":{  \n" +
//                "            \"label\":{  \n" +
//                "               \"enabled\":true,\n" +
//                "               \"fontSize\":14,\n" +
//                "               \"width\":42,\n" +
//                "               \"color\":\"#FFFFFFFF\",\n" +
//                "               \"font\":\"AvenirNext-Bold\",\n" +
//                "               \"height\":30,\n" +
//                "               \"labelsToSkip\":0,\n" +
//                "               \"position\":\"bottom\",\n" +
//                "               \"spaceBetweenLabels\":0\n" +
//                "            },\n" +
//                "            \"gridLine\":{  \n" +
//                "               \"width\":0.5,\n" +
//                "               \"enabled\":true,\n" +
//                "               \"color\":\"#00000000\"\n" +
//                "            },\n" +
//                "            \"axisLine\":{  \n" +
//                "               \"width\":1,\n" +
//                "               \"enabled\":true,\n" +
//                "               \"color\":\"#FFFFFFFF\"\n" +
//                "            }\n" +
//                "         },\n" +
//                "         \"datasets\":{  \n" +
//                "            \"dataset\":{  \n" +
//                "               \"label\":{  \n" +
//                "                  \"color\":\"#FFFFFFFF\",\n" +
//                "                  \"enabled\":true,\n" +
//                "                  \"fontSize\":35,\n" +
//                "                  \"font\":\"AvenirNext-Regular\"\n" +
//                "               },\n" +
//                "               \"colors\":[  \n" +
//                "                  \"#FFFFFFAA\"\n" +
//                "               ],\n" +
//                "               \"xVals\":[  \n" +
//                "                  \"3-8\nAvril\",\n" +
//                "                  \"10-16\nAvril\",\n" +
//                "                  \"17-23\nAvril\",\n" +
//                "                  \"25-30\nAvril\"\n" +
//                "               ],\n" +
//                "               \"yVals\":[  \n" +
//                "                  10,\n" +
//                "                  12,\n" +
//                "                  16,\n" +
//                "                  20\n" +
//                "               ]\n" +
//                "            },\n" +
//                "            \"dataset2\":{  \n" +
//                "               \"label\":{  \n" +
//                "                  \"color\":\"#FFFFFFFF\",\n" +
//                "                  \"enabled\":true,\n" +
//                "                  \"fontSize\":35,\n" +
//                "                  \"font\":\"AvenirNext-Regular\"\n" +
//                "               },\n" +
//                "               \"colors\":[  \n" +
//                "                  \"#673AB7AA\"\n" +
//                "               ],\n" +
//                "               \"xVals\":[  \n" +
//                "                  \"3-8\nAvril\",\n" +
//                "                  \"10-16\nAvril\",\n" +
//                "                  \"17-23\nAvril\",\n" +
//                "                  \"25-30\nAvril\"\n" +
//                "               ],\n" +
//                "               \"yVals\":[  \n" +
//                "                  1,\n" +
//                "                  4,\n" +
//                "                  15,\n" +
//                "                  22\n" +
//                "               ]\n" +
//                "            }\n" +
//                "         },\n" +
//                "         \"noDataLabel\":{  \n" +
//                "            \"color\":\"#FFFFFFFF\",\n" +
//                "            \"enabled\":true,\n" +
//                "            \"fontSize\":40,\n" +
//                "            \"font\":\"AvenirNext-Regular\"\n" +
//                "         },\n" +
//                "         \"maxFractionDigits\":0,\n" +
//                "         \"noDataMessage\":\"No data\",\n" +
//                "         \"subtitle\":\"01 Avril au 25 Avril 2016\",\n" +
//                "         \"offsets\":{  \n" +
//                "            \"left\":26,\n" +
//                "            \"right\":0,\n" +
//                "            \"bottom\":15,\n" +
//                "            \"top\":0\n" +
//                "         },\n" +
//                "         \"backgroundColor\":\"#01BCD4FF\",\n" +
//                "         \"legend\":{  \n" +
//                "            \"position\":\"belowChartLeft\",\n" +
//                "            \"label\":{  \n" +
//                "               \"fontSize\":14,\n" +
//                "               \"enabled\":true,\n" +
//                "               \"font\":\"HelveticaNeue-Bold\",\n" +
//                "               \"color\":\"#FFFFFF\"\n" +
//                "            },\n" +
//                "            \"form\":\"circle\"\n" +
//                "         }\n" +
//                "      },\n" +
//                "      {  \n" +
//                "         \"title\":\"NOMBRE DE TRANSACTIONS EFFECTUÉES\",\n" +
//                "         \"rightAxis\":{  \n" +
//                "            \"label\":{  \n" +
//                "               \"font\":\"AvenirNext-Bold\",\n" +
//                "               \"enabled\":false,\n" +
//                "               \"fontSize\":14,\n" +
//                "               \"color\":\"#00000000\"\n" +
//                "            },\n" +
//                "            \"gridLine\":{  \n" +
//                "               \"width\":0.5,\n" +
//                "               \"enabled\":false,\n" +
//                "               \"color\":\"#00000000\"\n" +
//                "            },\n" +
//                "            \"position\":\"outsideChart\",\n" +
//                "            \"axisLine\":{  \n" +
//                "               \"enabled\":false,\n" +
//                "               \"color\":\"#00000000\",\n" +
//                "               \"width\":0.5\n" +
//                "            }\n" +
//                "         },\n" +
//                "         \"leftAxis\":{  \n" +
//                "            \"label\":{  \n" +
//                "               \"font\":\"AvenirNext-Bold\",\n" +
//                "               \"enabled\":false,\n" +
//                "               \"fontSize\":14,\n" +
//                "               \"color\":\"#00000000\"\n" +
//                "            },\n" +
//                "            \"gridLine\":{  \n" +
//                "               \"width\":0.5,\n" +
//                "               \"enabled\":true,\n" +
//                "               \"color\":\"#FFFFFFFF\"\n" +
//                "            },\n" +
//                "            \"position\":\"outsideChart\",\n" +
//                "            \"axisLine\":{  \n" +
//                "               \"enabled\":false,\n" +
//                "               \"color\":\"#00000000\",\n" +
//                "               \"width\":0.5\n" +
//                "            }\n" +
//                "         },\n" +
//                "         \"drawValueAboveBar\":true,\n" +
//                "         \"interactionEnabled\":false,\n" +
//                "         \"gridBackgroundColor\":\"#00000000\",\n" +
//                "         \"showsFilterButtons\":false,\n" +
//                "         \"type\":\"VerticalBarChart\",\n" +
//                "         \"detailedDescription\":\"Detailed description\",\n" +
//                "         \"xAxis\":{  \n" +
//                "            \"label\":{  \n" +
//                "               \"enabled\":true,\n" +
//                "               \"fontSize\":14,\n" +
//                "               \"width\":42,\n" +
//                "               \"color\":\"#FFFFFFFF\",\n" +
//                "               \"font\":\"AvenirNext-Bold\",\n" +
//                "               \"height\":50,\n" +
//                "               \"labelsToSkip\":0,\n" +
//                "               \"position\":\"bottom\",\n" +
//                "               \"spaceBetweenLabels\":0\n" +
//                "            },\n" +
//                "            \"gridLine\":{  \n" +
//                "               \"width\":0.5,\n" +
//                "               \"enabled\":true,\n" +
//                "               \"color\":\"#FFFFFFAA\"\n" +
//                "            },\n" +
//                "            \"axisLine\":{  \n" +
//                "               \"width\":1,\n" +
//                "               \"enabled\":true,\n" +
//                "               \"color\":\"#FFFFFFFF\"\n" +
//                "            }\n" +
//                "         },\n" +
//                "         \"datasets\":{  \n" +
//                "            \"dataset\":{  \n" +
//                "               \"label\":{  \n" +
//                "                  \"color\":\"#FFFFFFFF\",\n" +
//                "                  \"enabled\":true,\n" +
//                "                  \"fontSize\":35,\n" +
//                "                  \"font\":\"AvenirNext-Regular\"\n" +
//                "               },\n" +
//                "               \"colors\":[  \n" +
//                "                  \"#FFFFFFAA\"\n" +
//                "               ],\n" +
//                "               \"xVals\":[  \n" +
//                "                  \"3-8\\nAvril\",\n" +
//                "                  \"10-16\\nAvril\",\n" +
//                "                  \"17-23\\nAvril\",\n" +
//                "                  \"25-30\\nAvril\"\n" +
//                "               ],\n" +
//                "               \"yVals\":[  \n" +
//                "                  10,\n" +
//                "                  12,\n" +
//                "                  16,\n" +
//                "                  20\n" +
//                "               ]\n" +
//                "            },\n" +
//                "            \"dataset2\":{  \n" +
//                "               \"label\":{  \n" +
//                "                  \"color\":\"#FFFFFFFF\",\n" +
//                "                  \"enabled\":true,\n" +
//                "                  \"fontSize\":35,\n" +
//                "                  \"font\":\"AvenirNext-Regular\"\n" +
//                "               },\n" +
//                "               \"colors\":[  \n" +
//                "                  \"#FF6D00AA\"\n" +
//                "               ],\n" +
//                "               \"xVals\":[  \n" +
//                "                  \"3-8\\nAvril\",\n" +
//                "                  \"10-16\\nAvril\",\n" +
//                "                  \"17-23\\nAvril\",\n" +
//                "                  \"25-30\\nAvril\"\n" +
//                "               ],\n" +
//                "               \"yVals\":[  \n" +
//                "                  1,\n" +
//                "                  4,\n" +
//                "                  15,\n" +
//                "                  22\n" +
//                "               ]\n" +
//                "            }\n" +
//                "         },\n" +
//                "         \"noDataLabel\":{  \n" +
//                "            \"color\":\"#FFFFFFFF\",\n" +
//                "            \"enabled\":true,\n" +
//                "            \"fontSize\":40,\n" +
//                "            \"font\":\"AvenirNext-Regular\"\n" +
//                "         },\n" +
//                "         \"maxFractionDigits\":0,\n" +
//                "         \"noDataMessage\":\"No data\",\n" +
//                "         \"subtitle\":\"01 Avril au 25 Avril 2016\",\n" +
//                "         \"offsets\":{  \n" +
//                "            \"left\":0,\n" +
//                "            \"right\":0,\n" +
//                "            \"bottom\":15,\n" +
//                "            \"top\":0\n" +
//                "         },\n" +
//                "         \"backgroundColor\":\"#9DCB6BFF\",\n" +
//                "         \"legend\":{  \n" +
//                "            \"position\":\"belowChartLeft\",\n" +
//                "            \"label\":{  \n" +
//                "               \"fontSize\":14,\n" +
//                "               \"enabled\":false,\n" +
//                "               \"font\":\"HelveticaNeue-Bold\",\n" +
//                "               \"color\":\"#FFFFFF\"\n" +
//                "            },\n" +
//                "            \"form\":\"circle\"\n" +
//                "         }\n" +
//                "      },\n" +
//                "      {  \n" +
//                "         \"title\":\"MONTANT DU CA GÉNÉRÉ\",\n" +
//                "         \"rightAxis\":{  \n" +
//                "            \"label\":{  \n" +
//                "               \"font\":\"HelveticaNeue-Bold\",\n" +
//                "               \"enabled\":false,\n" +
//                "               \"fontSize\":14,\n" +
//                "               \"color\":\"#00000000\"\n" +
//                "            },\n" +
//                "            \"gridLine\":{  \n" +
//                "               \"width\":0.5,\n" +
//                "               \"enabled\":true,\n" +
//                "               \"color\":\"#009688FF\"\n" +
//                "            },\n" +
//                "            \"position\":\"outsideChart\",\n" +
//                "            \"axisLine\":{  \n" +
//                "               \"enabled\":true,\n" +
//                "               \"color\":\"#00000000\",\n" +
//                "               \"width\":0.5\n" +
//                "            }\n" +
//                "         },\n" +
//                "         \"leftAxis\":{  \n" +
//                "            \"label\":{  \n" +
//                "               \"font\":\"HelveticaNeue-Bold\",\n" +
//                "               \"enabled\":false,\n" +
//                "               \"fontSize\":14,\n" +
//                "               \"color\":\"#00000000\"\n" +
//                "            },\n" +
//                "            \"gridLine\":{  \n" +
//                "               \"width\":0.5,\n" +
//                "               \"enabled\":false,\n" +
//                "               \"color\":\"#00000000\"\n" +
//                "            },\n" +
//                "            \"position\":\"outsideChart\",\n" +
//                "            \"axisLine\":{  \n" +
//                "               \"enabled\":false,\n" +
//                "               \"color\":\"#00000000\",\n" +
//                "               \"width\":0.5\n" +
//                "            }\n" +
//                "         },\n" +
//                "         \"interactionEnabled\":false,\n" +
//                "         \"gridBackgroundColor\":\"#00000000\",\n" +
//                "         \"showsFilterButtons\":false,\n" +
//                "         \"type\":\"LineChart\",\n" +
//                "         \"detailedDescription\":\"Detailed description\",\n" +
//                "         \"xAxis\":{  \n" +
//                "            \"label\":{  \n" +
//                "               \"enabled\":true,\n" +
//                "               \"fontSize\":14,\n" +
//                "               \"width\":20,\n" +
//                "               \"color\":\"#FFFFFFFF\",\n" +
//                "               \"font\":\"AvenirNext-Bold\",\n" +
//                "               \"height\":20,\n" +
//                "               \"labelsToSkip\":0,\n" +
//                "               \"position\":\"bottom\",\n" +
//                "               \"spaceBetweenLabels\":0\n" +
//                "            },\n" +
//                "            \"gridLine\":{  \n" +
//                "               \"width\":0.5,\n" +
//                "               \"enabled\":true,\n" +
//                "               \"color\":\"#FFFFFFFF\"\n" +
//                "            },\n" +
//                "            \"axisLine\":{  \n" +
//                "               \"width\":1,\n" +
//                "               \"enabled\":true,\n" +
//                "               \"color\":\"#FFFFFFFF\"\n" +
//                "            }\n" +
//                "         },\n" +
//                "         \"datasets\":{  \n" +
//                "            \"dataset\":{  \n" +
//                "               \"colors\":[  \n" +
//                "                  \"#FFFFFF\"\n" +
//                "               ],\n" +
//                "               \"yVals\":[  \n" +
//                "                  7,\n" +
//                "                  8,\n" +
//                "                  21,\n" +
//                "                  19\n" +
//                "               ],\n" +
//                "               \"drawCircleHole\":true,\n" +
//                "               \"xVals\":[  \n" +
//                "                  \"3-8\\nAvril\",\n" +
//                "                  \"10-16\\nAvril\",\n" +
//                "                  \"17-23\\nAvril\",\n" +
//                "                  \"25-30\\nAvril\"\n" +
//                "               ],\n" +
//                "               \"label\":{  \n" +
//                "                  \"color\":\"#FFFFFFFF\",\n" +
//                "                  \"enabled\":true,\n" +
//                "                  \"fontSize\":30,\n" +
//                "                  \"font\":\"AvenirNext-Regular\"\n" +
//                "               },\n" +
//                "               \"cubicIntensity\":0.2,\n" +
//                "               \"circleColors\":[  \n" +
//                "                  \"#FFFFFFFF\"\n" +
//                "               ],\n" +
//                "               \"lineWidth\":2\n" +
//                "            },\n" +
//                "            \"dataset2\":{  \n" +
//                "               \"colors\":[  \n" +
//                "                  \"#01579B\"\n" +
//                "               ],\n" +
//                "               \"yVals\":[  \n" +
//                "                  1,\n" +
//                "                  4,\n" +
//                "                  15,\n" +
//                "                  22\n" +
//                "               ],\n" +
//                "               \"drawCircleHole\":true,\n" +
//                "               \"xVals\":[  \n" +
//                "                  \"3-8\\nAvril\",\n" +
//                "                  \"10-16\\nAvril\",\n" +
//                "                  \"17-23\\nAvril\",\n" +
//                "                  \"25-30\\nAvril\"\n" +
//                "               ],\n" +
//                "               \"label\":{  \n" +
//                "                  \"color\":\"#FFFFFFFF\",\n" +
//                "                  \"enabled\":true,\n" +
//                "                  \"fontSize\":30,\n" +
//                "                  \"font\":\"AvenirNext-Regular\"\n" +
//                "               },\n" +
//                "               \"cubicIntensity\":0.2,\n" +
//                "               \"circleColors\":[  \n" +
//                "                  \"#FFFFFFFF\"\n" +
//                "               ],\n" +
//                "               \"lineWidth\":2\n" +
//                "            }\n" +
//                "         },\n" +
//                "         \"noDataLabel\":{  \n" +
//                "            \"color\":\"#FFFFFFFF\",\n" +
//                "            \"enabled\":true,\n" +
//                "            \"fontSize\":40,\n" +
//                "            \"font\":\"AvenirNext-Regular\"\n" +
//                "         },\n" +
//                "         \"maxFractionDigits\":0,\n" +
//                "         \"noDataMessage\":\"No data\",\n" +
//                "         \"subtitle\":\"01 Avril au 25 Avril 2016\",\n" +
//                "         \"offsets\":{  \n" +
//                "            \"left\":35,\n" +
//                "            \"right\":35,\n" +
//                "            \"bottom\":15,\n" +
//                "            \"top\":0\n" +
//                "         },\n" +
//                "         \"backgroundColor\":\"#4DB6ACFF\",\n" +
//                "         \"legend\":{  \n" +
//                "            \"position\":\"belowChartLeft\",\n" +
//                "            \"label\":{  \n" +
//                "               \"fontSize\":14,\n" +
//                "               \"enabled\":false,\n" +
//                "               \"font\":\"HelveticaNeue-Bold\",\n" +
//                "               \"color\":\"#FFFFFF\"\n" +
//                "            },\n" +
//                "            \"form\":\"circle\"\n" +
//                "         }\n" +
//                "      },\n" +
//                "      {  \n" +
//                "         \"title\":\"NOMBRE DE CLIENTS CONSEILLÉS PAR MAGASIN\",\n" +
//                "         \"hole\":{  \n" +
//                "            \"enabled\":true,\n" +
//                "            \"radiusPercent\":0.3,\n" +
//                "            \"color\":\"#36474F\"\n" +
//                "         },\n" +
//                "         \"interactionEnabled\":false,\n" +
//                "         \"gridBackgroundColor\":\"#00000000\",\n" +
//                "         \"showsFilterButtons\":false,\n" +
//                "         \"type\":\"PieChart\",\n" +
//                "         \"detailedDescription\":\"Detailed description\",\n" +
//                "         \"datasets\":{  \n" +
//                "            \"dataset\":{  \n" +
//                "               \"label\":{  \n" +
//                "                  \"color\":\"#FFFFFFFF\",\n" +
//                "                  \"enabled\":true,\n" +
//                "                  \"fontSize\":35,\n" +
//                "                  \"font\":\"AvenirNext-Regular\"\n" +
//                "               },\n" +
//                "               \"colors\":[  \n" +
//                "                  \"#9CCC65\",\n" +
//                "                  \"#FFD500\",\n" +
//                "                  \"#00BCD4\"\n" +
//                "               ],\n" +
//                "               \"xVals\":[  \n" +
//                "                  \"Béthune\",\n" +
//                "                  \"Nîmes\",\n" +
//                "                  \"Lens\"\n" +
//                "               ],\n" +
//                "               \"yVals\":[  \n" +
//                "                  7,\n" +
//                "                  5,\n" +
//                "                  9\n" +
//                "               ]\n" +
//                "            }\n" +
//                "         },\n" +
//                "         \"noDataLabel\":{  \n" +
//                "            \"color\":\"#FFFFFFFF\",\n" +
//                "            \"enabled\":true,\n" +
//                "            \"fontSize\":40,\n" +
//                "            \"font\":\"AvenirNext-Regular\"\n" +
//                "         },\n" +
//                "         \"maxFractionDigits\":0,\n" +
//                "         \"noDataMessage\":\"No data\",\n" +
//                "         \"subtitle\":\"01 Avril au 25 Avril 2016\",\n" +
//                "         \"offsets\":{  \n" +
//                "            \"left\":0,\n" +
//                "            \"right\":0,\n" +
//                "            \"bottom\":0,\n" +
//                "            \"top\":0\n" +
//                "         },\n" +
//                "         \"backgroundColor\":\"#36474F\",\n" +
//                "         \"legend\":{  \n" +
//                "            \"position\":\"belowChartLeft\",\n" +
//                "            \"label\":{  \n" +
//                "               \"fontSize\":14,\n" +
//                "               \"enabled\":false,\n" +
//                "               \"font\":\"HelveticaNeue-Bold\",\n" +
//                "               \"color\":\"#FFFFFF\"\n" +
//                "            },\n" +
//                "            \"form\":\"circle\"\n" +
//                "         }\n" +
//                "      }\n" +
//                "   ]\n" +
//                "}";

        String str = "{  \n" +
                "   \"results\":[  \n" +
                "      {  \n" +
                "         \"title\":\"NOMBRE DE CLIENTS RECRUTÉS\",\n" +
                "         \"rightAxis\":{  \n" +
                "            \"label\":{  \n" +
                "               \"font\":\"HelveticaNeue-Bold\",\n" +
                "               \"enabled\":true,\n" +
                "               \"fontSize\":14,\n" +
                "               \"color\":\"#00000000\"\n" +
                "            },\n" +
                "            \"gridLine\":{  \n" +
                "               \"width\":0.5,\n" +
                "               \"enabled\":true,\n" +
                "               \"color\":\"#FFFFFFFF\"\n" +
                "            },\n" +
                "            \"position\":\"insideChart\",\n" +
                "            \"axisLine\":{  \n" +
                "               \"enabled\":true,\n" +
                "               \"color\":\"#00000000\",\n" +
                "               \"width\":0.5\n" +
                "            }\n" +
                "         },\n" +
                "         \"leftAxis\":{  \n" +
                "            \"label\":{  \n" +
                "               \"font\":\"HelveticaNeue-Bold\",\n" +
                "               \"enabled\":false,\n" +
                "               \"fontSize\":14,\n" +
                "               \"color\":\"#00000000\"\n" +
                "            },\n" +
                "            \"gridLine\":{  \n" +
                "               \"width\":0.5,\n" +
                "               \"enabled\":false,\n" +
                "               \"color\":\"#00000000\"\n" +
                "            },\n" +
                "            \"position\":\"outsideChart\",\n" +
                "            \"axisLine\":{  \n" +
                "               \"enabled\":false,\n" +
                "               \"color\":\"#00000000\",\n" +
                "               \"width\":0.5\n" +
                "            }\n" +
                "         },\n" +
                "         \"drawValueAboveBar\":true,\n" +
                "         \"interactionEnabled\":false,\n" +
                "         \"gridBackgroundColor\":\"#00000000\",\n" +
                "         \"showsFilterButtons\":false,\n" +
                "         \"type\":\"HorizontalBarChart\",\n" +
                "         \"detailedDescription\":\"Detailed description\",\n" +
                "         \"xAxis\":{  \n" +
                "            \"label\":{  \n" +
                "               \"enabled\":true,\n" +
                "               \"fontSize\":18,\n" +
                "               \"width\":42,\n" +
                "               \"color\":\"#FFFFFFFF\",\n" +
                "               \"font\":\"AvenirNext-Bold\",\n" +
                "               \"height\":30,\n" +
                "               \"labelsToSkip\":0,\n" +
                "               \"position\":\"bottom\",\n" +
                "               \"spaceBetweenLabels\":0\n" +
                "            },\n" +
                "            \"gridLine\":{  \n" +
                "               \"width\":0.5,\n" +
                "               \"enabled\":false,\n" +
                "               \"color\":\"#FFFFFFAA\"\n" +
                "            },\n" +
                "            \"axisLine\":{  \n" +
                "               \"width\":1,\n" +
                "               \"enabled\":true,\n" +
                "               \"color\":\"#FFFFFFFF\"\n" +
                "            }\n" +
                "         },\n" +
                "         \"datasets\":{  \n" +
                "            \"dataset\":{  \n" +
                "               \"label\":{  \n" +
                "                  \"color\":\"#FFFFFFFF\",\n" +
                "                  \"enabled\":true,\n" +
                "                  \"fontSize\":26,\n" +
                "                  \"font\":\"AvenirNext-Regular\"\n" +
                "               },\n" +
                "               \"colors\":[  \n" +
                "                  \"#FFFFFFAA\"\n" +
                "               ],\n" +
                "               \"xVals\":[  \n" +
                "                  \"8 June\",\n" +
                "                  \"9 June\",\n" +
                "                  \"10 June\",\n" +
                "                  \"11 June\"\n" +
                "               ],\n" +
                "               \"yVals\":[  \n" +
                "                  10,\n" +
                "                  12,\n" +
                "                  16,\n" +
                "                  20\n" +
                "               ]\n" +
                "            }\n" +
                "         },\n" +
                "         \"noDataLabel\":{  \n" +
                "            \"color\":\"#FFFFFFFF\",\n" +
                "            \"enabled\":true,\n" +
                "            \"fontSize\":40,\n" +
                "            \"font\":\"AvenirNext-Regular\"\n" +
                "         },\n" +
                "         \"maxFractionDigits\":0,\n" +
                "         \"noDataMessage\":\"No data\",\n" +
                "         \"subtitle\":\"01 Avril au 25 Avril 2016\",\n" +
                "         \"offsets\":{  \n" +
                "            \"left\":26,\n" +
                "            \"right\":0,\n" +
                "            \"bottom\":15,\n" +
                "            \"top\":0\n" +
                "         },\n" +
                "         \"backgroundColor\":\"#01BCD4FF\",\n" +
                "         \"legend\":{  \n" +
                "            \"position\":\"belowChartLeft\",\n" +
                "            \"label\":{  \n" +
                "               \"fontSize\":14,\n" +
                "               \"enabled\":false,\n" +
                "               \"font\":\"HelveticaNeue-Bold\",\n" +
                "               \"color\":\"#AABBCCDD\"\n" +
                "            },\n" +
                "            \"form\":\"circle\"\n" +
                "         }\n" +
                "      },\n" +
                "      {  \n" +
                "         \"title\":\"NOMBRE DE TRANSACTIONS EFFECTUÉES\",\n" +
                "         \"rightAxis\":{  \n" +
                "            \"label\":{  \n" +
                "               \"font\":\"AvenirNext-Bold\",\n" +
                "               \"enabled\":false,\n" +
                "               \"fontSize\":14,\n" +
                "               \"color\":\"#00000000\"\n" +
                "            },\n" +
                "            \"gridLine\":{  \n" +
                "               \"width\":0.5,\n" +
                "               \"enabled\":false,\n" +
                "               \"color\":\"#FFFFFF\"\n" +
                "            },\n" +
                "            \"position\":\"outsideChart\",\n" +
                "            \"axisLine\":{  \n" +
                "               \"enabled\":false,\n" +
                "               \"color\":\"#00000000\",\n" +
                "               \"width\":0.5\n" +
                "            }\n" +
                "         },\n" +
                "         \"leftAxis\":{  \n" +
                "            \"label\":{  \n" +
                "               \"font\":\"AvenirNext-Bold\",\n" +
                "               \"enabled\":false,\n" +
                "               \"fontSize\":18,\n" +
                "               \"color\":\"#00000000\"\n" +
                "            },\n" +
                "            \"gridLine\":{  \n" +
                "               \"width\":0.5,\n" +
                "               \"enabled\":true,\n" +
                "               \"color\":\"#FFFFFF\"\n" +
                "            },\n" +
                "            \"position\":\"outsideChart\",\n" +
                "            \"axisLine\":{  \n" +
                "               \"enabled\":false,\n" +
                "               \"color\":\"#00000000\",\n" +
                "               \"width\":0.5\n" +
                "            }\n" +
                "         },\n" +
                "         \"drawValueAboveBar\":true,\n" +
                "         \"interactionEnabled\":false,\n" +
                "         \"gridBackgroundColor\":\"#00000000\",\n" +
                "         \"showsFilterButtons\":false,\n" +
                "         \"type\":\"VerticalBarChart\",\n" +
                "         \"detailedDescription\":\"Detailed description\",\n" +
                "         \"xAxis\":{  \n" +
                "            \"label\":{  \n" +
                "               \"enabled\":true,\n" +
                "               \"fontSize\":18,\n" +
                "               \"width\":42,\n" +
                "               \"color\":\"#FFFFFFFF\",\n" +
                "               \"font\":\"AvenirNext-Bold\",\n" +
                "               \"height\":50,\n" +
                "               \"labelsToSkip\":0,\n" +
                "               \"position\":\"bottom\",\n" +
                "               \"spaceBetweenLabels\":0\n" +
                "            },\n" +
                "            \"gridLine\":{  \n" +
                "               \"width\":0.5,\n" +
                "               \"enabled\":false,\n" +
                "               \"color\":\"#FFFFFF\"\n" +
                "            },\n" +
                "            \"axisLine\":{  \n" +
                "               \"width\":1,\n" +
                "               \"enabled\":true,\n" +
                "               \"color\":\"#FFFFFFFF\"\n" +
                "            }\n" +
                "         },\n" +
                "         \"datasets\":{  \n" +
                "            \"dataset\":{  \n" +
                "               \"label\":{  \n" +
                "                  \"color\":\"#FFFFFFFF\",\n" +
                "                  \"enabled\":true,\n" +
                "                  \"fontSize\":26,\n" +
                "                  \"font\":\"AvenirNext-Regular\"\n" +
                "               },\n" +
                "               \"colors\":[  \n" +
                "                  \"#FFFFFFAA\"\n" +
                "               ],\n" +
                "               \"xVals\":[  \n" +
                "                  \"3-8\\nAvril\",\n" +
                "                  \"10-16\\nAvril\",\n" +
                "                  \"17-23\\nAvril\",\n" +
                "                  \"25-30\\nAvril\"\n" +
                "               ],\n" +
                "               \"yVals\":[  \n" +
                "                  10,\n" +
                "                  12,\n" +
                "                  16,\n" +
                "                  20\n" +
                "               ]\n" +
                "            }\n" +
                "         },\n" +
                "         \"noDataLabel\":{  \n" +
                "            \"color\":\"#FFFFFFFF\",\n" +
                "            \"enabled\":true,\n" +
                "            \"fontSize\":40,\n" +
                "            \"font\":\"AvenirNext-Regular\"\n" +
                "         },\n" +
                "         \"maxFractionDigits\":0,\n" +
                "         \"noDataMessage\":\"No data\",\n" +
                "         \"subtitle\":\"01 Avril au 25 Avril 2016\",\n" +
                "         \"offsets\":{  \n" +
                "            \"left\":0,\n" +
                "            \"right\":0,\n" +
                "            \"bottom\":15,\n" +
                "            \"top\":0\n" +
                "         },\n" +
                "         \"backgroundColor\":\"#9DCB6BFF\",\n" +
                "         \"legend\":{  \n" +
                "            \"position\":\"belowChartLeft\",\n" +
                "            \"label\":{  \n" +
                "               \"fontSize\":14,\n" +
                "               \"enabled\":false,\n" +
                "               \"font\":\"HelveticaNeue-Bold\",\n" +
                "               \"color\":\"#AABBCCDD\"\n" +
                "            },\n" +
                "            \"form\":\"circle\"\n" +
                "         }\n" +
                "      }\n" +
                "   \n" +
                "   ]\n" +
                "}";

        Gson gson = new Gson();

        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse(str);

        ShopStatisticsResponse response = new ShopStatisticsResponse();
        GsonSerializerHelper.fillDefaultsFields(response, je);

//        List<PR_Chart> listTest = gson.fromJson(jo, new ListParameterizedType(PR_Chart.class));
//        response.setValues(listTest);

        // Get List of Charts from Factory
        if (jo.getAsJsonObject().has("results")) {
            Type t = new ListParameterizedType(PR_Chart.class);
            List<PR_Chart> list = gson.fromJson(jo.getAsJsonObject().get("results"), t);
            response.setValues(list);
        }

        return response;
    }

}