precision mediump float;

// General parameters
uniform sampler2D from;
uniform sampler2D to;
uniform float progress;
varying vec2 vTextureCoord;
// Custom parameters
const vec2 size = vec2(4.0,4.0);
const float pause = 0.1;
const float dividerSize = 0.05;

const vec4 dividerColor = vec4(0.0, 0.0, 0.0, 1.0);
const float randomOffset = 0.1;

float rand (vec2 co) {
  return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

float getDelta(vec2 p) {
  vec2 rectanglePos = floor(vec2(size) * p);
  vec2 rectangleSize = vec2(1.0 / vec2(size).x, 1.0 / vec2(size).y);

  float top = rectangleSize.y * (rectanglePos.y + 1.0);
  float bottom = rectangleSize.y * rectanglePos.y;
  float left = rectangleSize.x * rectanglePos.x;
  float right = rectangleSize.x * (rectanglePos.x + 1.0);

  float minX = min(abs(p.x - left), abs(p.x - right));
  float minY = min(abs(p.y - top), abs(p.y - bottom));
  return min(minX, minY);
}

float getDividerSize() {
  vec2 rectangleSize = vec2(1.0 / vec2(size).x, 1.0 / vec2(size).y);
  return min(rectangleSize.x, rectangleSize.y) * dividerSize;
}

void showDivider (vec2 p) {
  float currentProg = progress / pause;

  float a = 1.0;
  if(getDelta(p) < getDividerSize()) {
    a = 1.0 - currentProg;
  }

  gl_FragColor = mix(dividerColor, texture2D(from, p), a);
}

void hideDivider (vec2 p) {
  float currentProg = (progress - 1.0 + pause) / pause;

  float a = 1.0;
  if(getDelta(p) < getDividerSize()) {
    a = currentProg;
  }

  gl_FragColor = mix(dividerColor, texture2D(to, p), a);
}

void main() {
  if(progress < pause) {
    showDivider(vTextureCoord);
  } else if(progress < 1.0 - pause){
    if(getDelta(vTextureCoord) < getDividerSize()) {
      gl_FragColor = dividerColor;
    } else {
      float currentProg = (progress - pause) / (1.0 - pause * 2.0);
      vec2 q = vTextureCoord;
      vec2 rectanglePos = floor(vec2(size) * q);

      float r = rand(rectanglePos) - randomOffset;
      float cp = smoothstep(0.0, 1.0 - r, currentProg);

      float rectangleSize = 1.0 / vec2(size).x;
      float delta = rectanglePos.x * rectangleSize;
      float offset = rectangleSize / 2.0 + delta;

    vec2 ta = vTextureCoord;
      ta.x = (vTextureCoord.x - offset)/abs(cp - 0.5)*0.5 + offset;
      vec4 a = texture2D(from, ta);
      vec4 b = texture2D(to, ta);

      float s = step(abs(vec2(size).x * (q.x - delta) - 0.5), abs(cp - 0.5));
      gl_FragColor = vec4(mix(b, a, step(cp, 0.5)).rgb * s, 1.0);
    }
  } else {
    hideDivider(vTextureCoord);
  }
}