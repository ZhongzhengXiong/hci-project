import range from 'lodash/range';

export const mockData: any = {
  'GET /api/event/detail/:id': {
    'id': 1,
    'name': 'Name of the event',
    'types': [
      { name: 'type1' },
      { name: 'type2' },
    ],
    'description': 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
    'image': './assets/imgs/placeholder.png',
    'initiator': {
      name: 'Initiator of this event',
      avatar: './assets/imgs/placeholder.png',
    },
    'startTime': '2018-12-15T18:00:00.000+08:00',
    'endTime': '2018-12-15T20:00:00.000+08:00',
    'address': '1000 Some road',
    'lowerBound': 5,
    'upperBound': 14,
    'currentAttendants': 6,
  },
  'GET /api/event-type/list': range(5).map(n => {
    return { id: n + 1, name: `Type ${n + 1}` }
  }),
  'GET /api/notif/notif-detail/:id': {
    'type': 1,
    'title': 'Title 1',
    'content': 'Content 1',
  },
  'GET /api/personal/events-joined': [
    { path: './assets/imgs/placeholder.png', title: 'Title 5', id: 5, status: 'canceled'},
    { path: './assets/imgs/placeholder.png', title: 'Title 1', id: 1 , status: 'notstarted'},
    { path: './assets/imgs/placeholder.png', title: 'Title 2', id: 2, status: 'notstarted'},
    { path: './assets/imgs/placeholder.png', title: 'Title 3', id: 3, status: 'started'},
    { path: './assets/imgs/placeholder.png', title: 'Title 6', id: 3, status: 'started'},
    { path: './assets/imgs/placeholder.png', title: 'Title 4', id: 4, status: 'ended'},
  ],
  'GET /api/personal/events-released': [
    { path: './assets/imgs/placeholder.png', title: 'Title 5', id: 5, status: 'canceled'},
    { path: './assets/imgs/placeholder.png', title: 'Title 1', id: 1 , status: 'notstarted'},
    { path: './assets/imgs/placeholder.png', title: 'Title 2', id: 2, status: 'notstarted'},
    { path: './assets/imgs/placeholder.png', title: 'Title 3', id: 3, status: 'started'},
    { path: './assets/imgs/placeholder.png', title: 'Title 6', id: 3, status: 'started'},
    { path: './assets/imgs/placeholder.png', title: 'Title 4', id: 4, status: 'ended'},
  ],
  'GET /api/event/checkin/:id': {
    'person': [
      {id: 0, name: 'student1', type: 0},
      {id: 1, name: 'student2', type: 1},
      {id: 2, name: 'student3', type: 0},
      {id: 3, name: 'student4', type: 1},
      {id: 4, name: 'student5', type: 0},
      {id: 5, name: 'student6', type: 0},
    ]
  },

  'GET /api/event/home-flow': [
    { id: 0, path: './assets/imgs/placeholder.png', title: 'Title 1', status: 'notstarted' },
    { id: 1, path: './assets/imgs/placeholder.png', title: 'Title 2', status: 'started'},
    { id: 2, path: './assets/imgs/placeholder.png', title: 'Title 3', status: 'started'},
    { id: 3, path: './assets/imgs/placeholder.png', title: 'Title 4, This title seems to be longer', status: 'started'},
    { id: 4, path: './assets/imgs/placeholder.png', title: 'Title 5', status: 'ended' },
    { id: 5, path: './assets/imgs/placeholder.png', title: 'Title 6', status: 'ended' },
  ],
  'GET /api/notif/notif-list': [
    { id: 0, type: 1, time:'2018-1-1', addresser:'用户A', title:'活动1', content: 'Content 1' },
    { id: 1, type: 2, time:'2018-1-2', addresser:'用户B', title:'活动2', content: 'Content 2' },
    { id: 2, type: 2, time:'2018-1-3', addresser:'用户C', title:'活动3, testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest', content: 'Content 3, testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest' },
  ],
  'GET /api/picture/my-picture': [
    { id: 0, event_id: 0, title: '活动1', path: './assets/imgs/placeholder.png' },
    { id: 1, event_id: 1, title: '活动2', path: './assets/imgs/placeholder.png' },
    { id: 2, event_id: 2, title: '活动3', path: './assets/imgs/placeholder.png' },
    { id: 3, event_id: 3, title: '活动4', path: './assets/imgs/placeholder.png' },
    { id: 4, event_id: 4, title: '活动5', path: './assets/imgs/placeholder.png' },
    { id: 5, event_id: 5, title: '活动6', path: './assets/imgs/placeholder.png' },
    { id: 6, event_id: 6, title: '活动7', path: './assets/imgs/placeholder.png' },
  ],
  'GET /api/picture/event-picture/:id': {
    'picture': [
      { id: 0, uploader:'用户A', path: './assets/imgs/placeholder.png' },
      { id: 1, uploader:'用户B', path: './assets/imgs/placeholder.png' },
      { id: 2, uploader:'用户C', path: './assets/imgs/placeholder.png' },
      { id: 3, uploader:'用户D', path: './assets/imgs/placeholder.png' },
      { id: 4, uploader:'用户E', path: './assets/imgs/placeholder.png' },
    ]
  },
  'GET /api/event/event-comment/:id': {
    'comment': [
      { id: 0, uploader:'用户A', head:'./assets/imgs/placeholder.png', content: 'comment1' },
      { id: 1, uploader:'用户B', head:'./assets/imgs/placeholder.png', content: 'comment2' },
      { id: 2, uploader:'用户C', head:'./assets/imgs/placeholder.png', content: 'comment3' },
      { id: 3, uploader:'用户D', head:'./assets/imgs/placeholder.png', content: 'comment4' },
      { id: 4, uploader:'用户E', head:'./assets/imgs/placeholder.png', content: 'comment5' },
    ]
  }
};
