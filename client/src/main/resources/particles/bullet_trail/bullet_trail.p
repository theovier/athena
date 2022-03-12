{
  "emitters": [
    {
      "name": "smoke_trail",
      "modules": [
        {
          "class": "ParticleModule",
          "index": 0
        },
        {
          "class": "EmitterModule",
          "index": 1,
          "delay": 0,
          "duration": 2,
          "rate": 50
        },
        {
          "class": "InputModule",
          "index": 2,
          "scopeKey": 0
        },
        {
          "class": "Vector2Module",
          "index": 3,
          "x": 0,
          "y": 0
        },
        {
          "class": "RandomRangeModule",
          "index": 4,
          "min": 0.15,
          "max": 0.15,
          "distributed": false
        },
        {
          "class": "RandomRangeModule",
          "index": 5,
          "min": 10,
          "max": 10,
          "distributed": false
        },
        {
          "class": "DynamicRangeModule",
          "index": 6,
          "points": [
            {
              "x": 0,
              "y": 1
            }
          ],
          "lowMin": 12,
          "lowMax": 12,
          "highMin": 15,
          "highMax": 15
        },
        {
          "class": "DynamicRangeModule",
          "index": 7,
          "points": [
            {
              "x": 0,
              "y": 1
            },
            {
              "x": 0.65753424,
              "y": 1
            },
            {
              "x": 1,
              "y": 0.3
            }
          ],
          "lowMin": 0,
          "lowMax": 0,
          "highMin": 0.75,
          "highMax": 0.75
        },
        {
          "class": "DynamicRangeModule",
          "index": 8,
          "points": [
            {
              "x": 0,
              "y": 1
            },
            {
              "x": 0.4,
              "y": 0.40
            }
          ],
          "lowMin": 0,
          "lowMax": 0,
          "highMin": 0.3,
          "highMax": 0.3
        },
        {
          "class": "GradientColorModule",
          "index": 9,
          "points": [
            {
              "r": 1,
              "g": 1,
              "b": 1,
              "pos": 0
            }
          ]
        },
        {
          "class": "DynamicRangeModule",
          "index": 10,
          "points": [
            {
              "x": 0,
              "y": 1
            },
            {
              "x": 0.31506848,
              "y": 1
            },
            {
              "x": 0.80136985,
              "y": 0.5
            },
            {
              "x": 1,
              "y": 0
            }
          ],
          "lowMin": 0,
          "lowMax": 0,
          "highMin": 1,
          "highMax": 1
        },
        {
          "class": "StaticValueModule",
          "index": 11,
          "value": 0
        },
        {
          "class": "EmConfigModule",
          "index": 12,
          "additive": true,
          "isBlendAdd": false,
          "attached": false,
          "continuous": true,
          "aligned": false,
          "immortal": false
        },
        {
          "class": "TextureModule",
          "index": 13,
          "regionName": "smoke"
        }
      ],
      "connections": [
        {
          "moduleFrom": 3,
          "slotTo": 3
        },
        {
          "moduleFrom": 4,
          "moduleTo": 1
        },
        {
          "moduleFrom": 5,
          "moduleTo": 1,
          "slotTo": 1
        },
        {
          "moduleFrom": 6,
          "moduleTo": 1,
          "slotTo": 2
        },
        {
          "moduleFrom": 7,
          "slotTo": 4
        },
        {
          "moduleFrom": 8,
          "slotTo": 13
        },
        {
          "moduleFrom": 9,
          "slotTo": 9
        },
        {
          "moduleFrom": 10,
          "slotTo": 10
        },
        {
          "moduleFrom": 11,
          "slotTo": 11
        },
        {
          "moduleFrom": 12,
          "moduleTo": 1,
          "slotTo": 3
        },
        {
          "moduleFrom": 13,
          "slotTo": 2
        },
        {
          "moduleFrom": 2,
          "moduleTo": 7
        },
        {
          "moduleFrom": 2,
          "moduleTo": 6
        }
      ]
    }
  ],
  "metadata": {
    "resources": [
      "smoke"
    ]
  }
}