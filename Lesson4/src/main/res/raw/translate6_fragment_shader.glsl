precision mediump float;

// General parameters
uniform sampler2D from,to;
uniform float progress;
varying vec2 vTextureCoord;

const float amplitude = 30.0;
const float speed = 30.0;

void main()
{
  vec2 dir = vTextureCoord - vec2(.5);
  float dist = length(dir);

  if (dist > progress) {
    gl_FragColor = mix(texture2D(from, vTextureCoord), texture2D(to, vTextureCoord), progress);
  } else {
    vec2 offset = dir * sin(dist * amplitude - progress * speed);
    gl_FragColor = mix(texture2D(from, vTextureCoord + offset), texture2D(to, vTextureCoord), progress);
  }
}