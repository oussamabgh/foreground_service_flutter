import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'PLAY MUSIC',
      theme: ThemeData(
        primarySwatch: Colors.red,
      ),
      home: MyHomePage(title: 'Play mp3 music'),
    );
  }
}
class MyHomePage extends StatefulWidget {
  MyHomePage({Key? key, required this.title}) : super(key: key);
  final String title;
  @override
  _MyHomePageState createState() => _MyHomePageState();
}
class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;
  static const channel = MethodChannel('lightacademy/channel');
  on() async {
    try {
      await channel.invokeMethod('on');
    } on PlatformException catch (ex) {
      print(ex.message);
    }
  }
  off() async {
    try {
      await channel.invokeMethod('off');
    } on PlatformException catch (ex) {
      print(ex.message);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(

        title: Text(widget.title),
      ),
      body: Center(

        child: Column(

          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'Play',
            ),
            Row(
              children: <Widget>[
                Expanded(
                  child: Padding(
                    padding: const EdgeInsets.all(7.1),
                    child: RaisedButton(
                      onPressed: on,
                      child: Text('on'),
                    ),
                  ),
                ),
                Expanded(
                  child: Padding(
                    padding: const EdgeInsets.all(7.1),
                    child: RaisedButton(
                      onPressed: off,
                      child: Text('off'),
                    ),
                  ),
                ),
              ],
            )
          ],
        ),
      ),
    );
  }
}
