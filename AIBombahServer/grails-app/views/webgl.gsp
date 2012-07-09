<!DOCTYPE html>
<html>
<head>
<title>Bombah</title>
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/thrift/thrift.js"></script>
<script type="text/javascript" src="gen-js/Bombah_types.js"></script>
<script type="text/javascript" src="gen-js/BombahService.js"></script>
<script type="text/javascript" src="js/webgl-utils.js"></script>
<script type="text/javascript">
    var url = "bombah/json"
    var canvas;
    var gl;
    var vertexShader;
    var fragmentShader;
    var program;
    var textures = [];
    var images = [];
    var playerTextures = [];
    var playerImages = [];
    var client;
    var gameInfo;

    $(document).ready(function () {
        canvas = document.getElementById("canvas");
        gl = getWebGLContext(canvas);
        vertexShader = createShaderFromScriptElement(gl, "2d-vertex-shader");
        fragmentShader = createShaderFromScriptElement(gl, "2d-fragment-shader");
        program = createProgram(gl, [vertexShader, fragmentShader]);

        loadTextures();
        observe(url);
    });

    function observe(url) {
        console.log("Connecting to " + url);

        var transport = new Thrift.Transport(url);
        var protocol = new Thrift.Protocol(transport);
        client = new BombahServiceClient(protocol);

        console.log("Waiting for match to start");
//            client.waitTicks(1);
//            client.waitForStart();
        console.log("Match started!");

        startPolling();
    }

    function startPolling() {
        try {
            gameInfo = client.getGameInfo(-1);
            console.log(gameInfo);
//            while( true ) {
//                updateMap();
//                client.waitTicks(10);
//            }
              window.setInterval(updateMap, 50);
        } catch (e) {

        }
    }

    function updateMap() {
        var mapState = client.getMapState(); // TODO: how to async?
//            console.log( mapState );
        // Bombah.thrift
        // Bombah.thrift
        $.each(mapState.tiles, function (i, tile) {
            var x = i % gameInfo.mapWidth;
            var y = (i - x) / gameInfo.mapWidth;
            var texture = textures[tile];
            var image = images[tile];
            drawTile(x * 64, y * 64, texture, image);
        });

        $.each(mapState.players, function (i, player) {
            if( player.alive ) {
                var x = player.x;
                var y = player.y;
                var texture = playerTextures[i];
                var image = playerImages[i];
                drawTile(x * 64, y * 64, texture, image);
            }
        });

        $.each(mapState.flames, function (i, flame) {
            var x = flame.coordinate.x;
            var y = flame.coordinate.y;
            var texture = textures[Tile.FIRE];
            var image = images[Tile.FIRE];
            drawTile(x * 64, y * 64, texture, image);
        });
    }

    function drawTile(x, y, texture, image) {
        gl.useProgram(program);

        // look up where the vertex data needs to go.
        var positionLocation = gl.getAttribLocation(program, "a_position");
        var texCoordLocation = gl.getAttribLocation(program, "a_texCoord");

        // provide texture coordinates for the rectangle.
        var texCoordBuffer = gl.createBuffer();
        gl.bindBuffer(gl.ARRAY_BUFFER, texCoordBuffer);
        gl.bufferData(gl.ARRAY_BUFFER, new Float32Array([
            0.0, 0.0,
            1.0, 0.0,
            0.0, 1.0,
            0.0, 1.0,
            1.0, 0.0,
            1.0, 1.0]), gl.STATIC_DRAW);
        gl.enableVertexAttribArray(texCoordLocation);
        gl.vertexAttribPointer(texCoordLocation, 2, gl.FLOAT, false, 0, 0);

        gl.bindTexture(gl.TEXTURE_2D, texture);

        // Set the parameters so we can render any size image.
        gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE);
        gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE);
        gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.NEAREST);
        gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.NEAREST);

        // Upload the image into the texture.
        gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, gl.RGBA, gl.UNSIGNED_BYTE, image);

        // lookup uniforms
        var resolutionLocation = gl.getUniformLocation(program, "u_resolution");

        // set the resolution
        gl.uniform2f(resolutionLocation, canvas.width, canvas.height);

        // Create a buffer for the position of the rectangle corners.
        var buffer = gl.createBuffer();
        gl.bindBuffer(gl.ARRAY_BUFFER, buffer);
        gl.enableVertexAttribArray(positionLocation);
        gl.vertexAttribPointer(positionLocation, 2, gl.FLOAT, false, 0, 0);

        // Set a rectangle the same size as the image.
        setRectangle(gl, x, y, 64, 64);

        // Draw the rectangle.
        gl.drawArrays(gl.TRIANGLES, 0, 6);
    }

    function setRectangle(gl, x, y, width, height) {
        var x1 = x;
        var x2 = x + width;
        var y1 = y;
        var y2 = y + height;
        gl.bufferData(gl.ARRAY_BUFFER, new Float32Array([
            x1, y1,
            x2, y1,
            x1, y2,
            x1, y2,
            x2, y1,
            x2, y2]), gl.STATIC_DRAW);
    }

    function loadTextures() {
        textures = [];

        $.each(Tile, function (i, o) {
            var file = "images/" + o + ".png";
            console.log("Loading " + file + " as " + i);
            var texture = gl.createTexture();
            textures.push(texture);
            var image = new Image();
            images.push(image);
            image.onload = function () {
//                handleTextureLoaded(image, texture, i);
            }
            image.src = file;
        });

        for (var i = 0; i < 4; i++) {
            var file = "images/player_" + i + ".png";
            console.log("Loading " + file + " as " + i);
            var texture = gl.createTexture();
            playerTextures.push(texture);
            var image = new Image();
            playerImages.push(image);
            image.onload = function () {
//                handleTextureLoaded(image, texture, i);
            }
            image.src = file;
        }

    }

    function handleTextureLoaded(image, texture, i) {
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
        vertexShader = createShaderFromScriptElement(gl, "2d-vertex-shader");
        fragmentShader = createShaderFromScriptElement(gl, "2d-fragment-shader");
        program = createProgram(gl, [vertexShader, fragmentShader]);
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
    attribute vec2 a_texCoord;

    uniform vec2 u_resolution;

    varying vec2 v_texCoord;

    void main() {
    // convert the rectangle from pixels to 0.0 to 1.0
    vec2 zeroToOne = a_position / u_resolution;

    // convert from 0->1 to 0->2
    vec2 zeroToTwo = zeroToOne * 2.0;

    // convert from 0->2 to -1->+1 (clipspace)
    vec2 clipSpace = zeroToTwo - 1.0;

    gl_Position = vec4(clipSpace * vec2(1, -1), 0, 1);

    // pass the texCoord to the fragment shader
    // The GPU will interpolate this value between points.
    v_texCoord = a_texCoord;
    }
</script>

<script id="2d-fragment-shader" type="x-shader/x-fragment">
    precision mediump float;

    // our texture
    uniform sampler2D u_image;

    // the texCoords passed in from the vertex shader.
    varying vec2 v_texCoord;

    void main() {
    gl_FragColor = texture2D(u_image, v_texCoord);
    }
</script>
</head>

<body>
<canvas id="canvas" width="832" height="704"></canvas>
</body>
</html>