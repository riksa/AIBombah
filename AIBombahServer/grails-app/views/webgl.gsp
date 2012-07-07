<!DOCTYPE html>
<html>
<head>
    <title>WebGL Visualizer test</title>
    <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="js/thrift/thrift.js"></script>
    <script type="text/javascript" src="gen-js/Bombah_types.js"></script>
    <script type="text/javascript" src="gen-js/BombahService.js"></script>
    <script type="text/javascript" src="js/webgl-utils.js"></script>
    <script type="text/javascript">
        var url = "bombah/json"
        var canvas;
        var gl;
        var textures = [];
        var client;
        var mapInfo;

        $(document).ready(function () {
            canvas = document.getElementById("canvas");
            gl = getWebGLContext(canvas);

            loadTextures();
            observe(url);
        });

        function observe(url) {
            console.log("Connecting to " + url);

            var transport = new Thrift.Transport(url);
            var protocol = new Thrift.Protocol(transport);
            client = new BombahServiceClient(protocol);

            console.log( "Waiting for match to start");
//            client.waitTicks(1);
//            client.waitForStart();
            console.log( "Match started!");

            startPolling();
        }

        function startPolling() {
            try {
                mapInfo = client.getGameInfo( -1 );
                window.setInterval( updateMap, 1000 );
            } catch (e) {

            }
        }

        function updateMap() {
            var mapState = client.getMapState(); // TODO: how to async?
//            console.log( mapState );
            console.log( "Ticks remaining "+mapState.ticksRemaining);
            $.each( mapState.tiles, function(i, t) {
            });
            $.each( mapState.players, function(i, p) {
//                console.log( p );
            });
        }

        function loadTextures() {
            textures = [];

            $.each(Tile, function (i, o) {
                var file = "images/" + o + ".png";
                console.log("Loading " + file + " as " + i);
                var texture = gl.createTexture();
                textures.push(texture);
                var image = new Image();
                image.onload = function () {
                    handleTextureLoaded(image, texture);
                }
                image.src = file;
            });

        }

        function initTextures() {
        }

        function handleTextureLoaded(image, texture) {
            gl.bindTexture(gl.TEXTURE_2D, texture);
            gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, gl.RGBA, gl.UNSIGNED_BYTE, image);
            gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR);
            gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR_MIPMAP_NEAREST);
            gl.generateMipmap(gl.TEXTURE_2D);
            gl.bindTexture(gl.TEXTURE_2D, null);
        }

        function main() {
            // Get A WebGL context

            // setup a GLSL program
            var vertexShader = createShaderFromScriptElement(gl, "2d-vertex-shader");
            var fragmentShader = createShaderFromScriptElement(gl, "2d-fragment-shader");
            var program = createProgram(gl, [vertexShader, fragmentShader]);
            gl.useProgram(program);

            // look up where the vertex data needs to go.
            var positionLocation = gl.getAttribLocation(program, "a_position");

            // Create a buffer and put a single clipspace rectangle in
            // it (2 triangles)
            var buffer = gl.createBuffer();
            gl.bindBuffer(gl.ARRAY_BUFFER, buffer);
            gl.bufferData(
                    gl.ARRAY_BUFFER,
                    new Float32Array([
                        -1.0, -1.0,
                        1.0, -1.0,
                        -1.0, 1.0,
                        -1.0, 1.0,
                        1.0, -1.0,
                        1.0, 1.0]),
                    gl.STATIC_DRAW);
            gl.enableVertexAttribArray(positionLocation);
            gl.vertexAttribPointer(positionLocation, 2, gl.FLOAT, false, 0, 0);

            // draw
            gl.drawArrays(gl.TRIANGLES, 0, 6);
        }
    </script>

    <script id="2d-vertex-shader" type="x-shader/x-vertex">
        attribute vec2 a_position;

        void main() {
        gl_Position = vec4(a_position, 0, 1);
        }
    </script>

    <script id="2d-fragment-shader" type="x-shader/x-fragment">
        void main() {
        gl_FragColor = vec4(0,1,0,1); // green
        }
    </script>
</head>
<body>
<canvas id="canvas" width="400" height="300"></canvas>
</body>
</html>