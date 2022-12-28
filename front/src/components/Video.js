import styled from 'styled-components';
import ReactPlayer from 'react-player';

const Video = ({videoId}) => {
  return (
    <VideoWrapper
      url={`https://www.youtube.com/watch?v=${videoId}`}
      width="300"
      heigth="150"
    />
  )
}

export default Video;

const VideoWrapper = styled(ReactPlayer)`
  width:30rem;
  height:15rem !important;
  margin-right: 1rem;
`