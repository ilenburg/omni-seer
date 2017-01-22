uniform mat4 uMVPMatrix;
attribute vec4 vPosition;
attribute vec2 TexCoordIn;
varying vec2 TexCoordOut;
void main() {
    gl_Position = uMVPMatrix * vPosition;
    TexCoordOut = TexCoordIn;
}