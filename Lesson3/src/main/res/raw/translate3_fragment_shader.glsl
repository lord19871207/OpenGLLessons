precision mediump float;
// General parameters
uniform sampler2D from;
uniform sampler2D to;
uniform float progress;
varying vec2 vTextureCoord;

const float smoothness = 0.3;
const bool opening = true;

const vec2 center = vec2(0.5, 0.5);
const float SQRT_2 = 1.414213562373;

void main() {
  float x = opening ? progress : 1.-progress;
  float m = smoothstep(-smoothness, 0.0, SQRT_2*distance(center, vTextureCoord) - x*(1.+smoothness));
  gl_FragColor = mix(texture2D(from, vTextureCoord), texture2D(to, vTextureCoord), opening ? 1.-m : m);
}
