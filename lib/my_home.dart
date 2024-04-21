import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key});

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  String message='unknown';
  Map<dynamic,dynamic>dataMap={};
  static const platform=MethodChannel('test_native_channel_name');
  
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Text(message),
            ElevatedButton(onPressed:() => _getMessage(), child: Text('getMessage')),
            if(dataMap.isNotEmpty)
            Column(
              children: [
                Row(
                  children: [
                    Text('name:'),
                    Text(dataMap['name']),
                  ],
                ),
                Row(
                  children: [
                    Text('age:'),
                    Text(dataMap['age']),
                  ],
                ),
                Row(
                  children: [
                    Text('skills:'),
                    Text(dataMap['skills']),
                  ],
                ),
              ],
            ),
            ElevatedButton(onPressed:() => _getMap(), child: Text('getMAp'))
          ],
        ),
      ),
    );
  }
  
  Future<void>_getMessage()async{
    String messageReceive;
    try{
      messageReceive=await platform.invokeMethod('getMessageMethodNative');
    }on PlatformException catch(e){
      messageReceive='failed to get data :${e.message}';
    }catch(e){
      messageReceive='error :${e}';
    }
    setState(() {
      message=messageReceive;
    });
  }
  Future<void>_getMap()async{
    Map<dynamic,dynamic> map;
    try{
      map=await platform.invokeMethod('getMap');
    }on PlatformException catch(e){
      // print(e);
      map={};
    }catch(e){
      // print(e);
      map={};
    }
    setState(() {
      dataMap=map;
    });
  }
}
