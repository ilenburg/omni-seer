/*
uniform vec4 vColor;
uniform sampler2D TexCoordIn;
uniform float posX;
uniform float posY;
varying vec2 TexCoordOut;
void main() {
    gl_FragColor = texture2D(TexCoordIn, vec2(TexCoordOut.x + posX, TexCoordOut.y + posY ));
}*/

/*
uniform mat4 uMVPMatrix;
attribute vec4 vPosition;
attribute vec2 TexCoordIn;
varying vec2 TexCoordOut;

void main() {
    gl_Position = uMVPMatrix * vPosition;
    TexCoordOut = TexCoordIn;
}
*/