attribute vec2 a_Position;
attribute vec2 aTextureCoord;
varying vec2 vTextureCoord;

void main() {
  gl_Position = vec4(a_Position,0.0,1.0);
  vTextureCoord = aTextureCoord;
}
