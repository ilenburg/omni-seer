attribute vec4 vPosition;
attribute vec2 texCoordIn;
uniform mat4 uMVPMatrix;
varying vec2 texCoord;

void main() {
    gl_Position = uMVPMatrix * vPosition;
    texCoord = texCoordIn;
}