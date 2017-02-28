precision mediump float;
uniform sampler2D from, to;
uniform float progress;
varying vec2 vTextureCoord;
const float strength=0.1;

void main() {
  vec4 ca = texture2D(from, vTextureCoord);
  vec4 cb = texture2D(to, vTextureCoord);

  vec2 oa = (((ca.rg+ca.b)*0.5)*2.0-1.0);
  vec2 ob = (((cb.rg+cb.b)*0.5)*2.0-1.0);
  vec2 oc = mix(oa,ob,0.5)*strength;

  float w0 = progress;
  float w1 = 1.0-w0;
  gl_FragColor = mix(texture2D(from, vTextureCoord+oc*w0), texture2D(to, vTextureCoord-oc*w1), progress);
}